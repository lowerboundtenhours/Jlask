import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;

/**
 * Java alternative of itsdangerous.URLSafeTimeSerializer. Note that now it only support to encode
 * and sign HashMap<String, String>
 */
public class SigningSerializer {
  private Signature signature;
  private SecureRandom secureRandom;
  private KeyPairGenerator keyPairGenerator;
  private KeyPair keyPair;
  private Gson gson;
  private Type dictType;

  public SigningSerializer() {
    try {
      signature = Signature.getInstance("SHA256WithDSA");
      secureRandom = new SecureRandom();
      keyPairGenerator = KeyPairGenerator.getInstance("DSA");
      keyPair = keyPairGenerator.generateKeyPair();
      gson = new Gson();
      dictType = new TypeToken<HashMap<String, String>>() {}.getType();
      signature.initSign(keyPair.getPrivate(), secureRandom);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static SigningSerializer instance = null;

  public static SigningSerializer getInstance() {
    if (instance == null) {
      instance = new SigningSerializer();
    }
    return instance;
  }

  /** Dumps the json format string along with its siginature (base64 encdoed) of input dict. */
  public String dumps(HashMap<String, String> dict) {
    String jsonString = this.toJsonString(dict);
    byte[] digitalSignature = this.sign(jsonString);
    String encodedSignature = toBase64(digitalSignature);
    return jsonString + "." + encodedSignature;
  }

  /**
   * Load a json string with its base64-encoded siginature, verify the signature and return the
   * original content of the json string.
   */
  public HashMap<String, String> loads(String value) {
    String[] tokens = value.split(".");
    String jsonString = tokens[0];
    String encodedSignature = tokens[1];
    byte[] digitalSignature = this.fromBase64(encodedSignature);
    if (!this.verify(digitalSignature)) {
      throw new RuntimeException("Verification of session cookie failed.");
    }
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
      signature.update(data);
      byte[] digitalSignature = signature.sign();
      return digitalSignature;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private boolean verify(byte[] digitalSignature) {
    try {
      signature.initVerify(keyPair.getPublic());
      return signature.verify(digitalSignature);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private String toBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  private byte[] fromBase64(String base64str) {
    return Base64.getDecoder().decode(base64str);
  }
}
