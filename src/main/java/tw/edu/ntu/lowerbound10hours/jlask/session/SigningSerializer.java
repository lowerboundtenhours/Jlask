package tw.edu.ntu.lowerbound10hours.jlask.session;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

/**
 * Java alternative of itsdangerous.URLSafeTimeSerializer. Note that now it only support to encode
 * and sign a HashMap from String to String.
 */
public class SigningSerializer {
  private Signature signatureSigner;
  private Signature signatureVerifier;
  private SecureRandom secureRandom;
  private KeyPairGenerator keyPairGenerator;
  private KeyPair keyPair;
  private Gson gson;
  private Type dictType;

  /** Construct a SigningSerializer. */
  public SigningSerializer(String keyStoragePath) {
    try {
      secureRandom = new SecureRandom();
      if (keyStoragePath == null) {
        keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPair = keyPairGenerator.generateKeyPair();
      } else {
        try {
          keyPair = loadKeyPair(keyStoragePath, "DSA");
        } catch (Exception e) {
          keyPairGenerator = KeyPairGenerator.getInstance("DSA");
          keyPair = keyPairGenerator.generateKeyPair();
          saveKeyPair(keyStoragePath, keyPair);
        }
      }
      gson = new Gson();
      dictType = new TypeToken<HashMap<String, String>>() {}.getType();

      // Get signer and verifier. Note they need to be 2 objects
      // (suggested by
      // https://stackoverflow.com/questions/9283244/java-security-signature-object-not-initialized-for-signing)
      signatureSigner = Signature.getInstance("SHA256WithDSA");
      signatureSigner.initSign(keyPair.getPrivate(), secureRandom);
      signatureVerifier = Signature.getInstance("SHA256WithDSA");
      signatureVerifier.initVerify(keyPair.getPublic());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static SigningSerializer instance = null;

  /** Singleton of SigningSerializer. */
  public static SigningSerializer getInstance(String keyStoragePath) {
    if (instance == null) {
      instance = new SigningSerializer(keyStoragePath);
    }
    return instance;
  }

  /** Dumps the json format string along with its siginature (base64 encdoed) of input dict. */
  public String dumps(HashMap<String, String> dict) {
    String jsonString = this.toJsonString(dict);
    String jsonStringBase64 = this.stringToBase64(jsonString);

    byte[] digitalSignature = this.sign(jsonStringBase64);
    String encodedSignature = this.bytesToBase64(digitalSignature);

    return jsonStringBase64 + "." + encodedSignature;
  }

  /**
   * Load a json string with its base64-encoded siginature, verify the signatureand return the
   * original content of the json string.
   */
  public HashMap<String, String> loads(String value) {
    String[] tokens = value.split("\\.");
    String jsonStringBase64 = tokens[0];
    String encodedSignature = tokens[1];

    byte[] digitalSignature = this.bytesFromBase64(encodedSignature);
    if (!this.verify(jsonStringBase64, digitalSignature)) {
      throw new RuntimeException("Verification of session cookie failed.");
    }

    String jsonString = this.stringFromBase64(jsonStringBase64);
    HashMap<String, String> originalContent = this.fromJsonString(jsonString);
    return originalContent;
  }

  private String toJsonString(HashMap<String, String> dict) {
    return gson.toJson(dict);
  }

  private HashMap<String, String> fromJsonString(String jsonString) {
    return gson.fromJson(jsonString, dictType);
  }

  private byte[] sign(String target) {
    try {
      byte[] data = target.getBytes("UTF-8");
      signatureSigner.update(data);
      byte[] digitalSignature = signatureSigner.sign();
      return digitalSignature;
    } catch (SignatureException e) {
      e.printStackTrace();
      throw new RuntimeException();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private boolean verify(String target, byte[] digitalSignature) {
    try {
      byte[] data = target.getBytes("UTF-8");
      signatureVerifier.update(data);
      return signatureVerifier.verify(digitalSignature);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private String bytesToBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  private byte[] bytesFromBase64(String base64str) {
    return Base64.getDecoder().decode(base64str);
  }

  private String stringToBase64(String str) {
    return this.bytesToBase64(str.getBytes());
  }

  private String stringFromBase64(String base64str) {
    return new String(this.bytesFromBase64(base64str));
  }

  private void saveKeyPair(String path, KeyPair keyPair) throws IOException {
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    // Store Public Key.
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
    FileOutputStream fos = new FileOutputStream(path + "/public.key");
    fos.write(x509EncodedKeySpec.getEncoded());
    fos.close();

    // Store Private Key.
    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
    fos = new FileOutputStream(path + "/private.key");
    fos.write(pkcs8EncodedKeySpec.getEncoded());
    fos.close();
  }

  private KeyPair loadKeyPair(String path, String algorithm)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    // Read Public Key.
    File filePublicKey = new File(path + "/public.key");
    FileInputStream fis = new FileInputStream(path + "/public.key");
    byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
    fis.read(encodedPublicKey);
    fis.close();

    // Read Private Key.
    File filePrivateKey = new File(path + "/private.key");
    fis = new FileInputStream(path + "/private.key");
    byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
    fis.read(encodedPrivateKey);
    fis.close();

    // Generate KeyPair.
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

    return new KeyPair(publicKey, privateKey);
  }
}
