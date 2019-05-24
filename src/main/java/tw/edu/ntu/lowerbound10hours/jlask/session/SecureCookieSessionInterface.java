package tw.edu.ntu.lowerbound10hours.jlask.session;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class SecureCookieSessionInterface extends SessionInterface {
  public SecureCookieSessionInterface() {};

  private String salt = "cookie-session";

  public SigningSerializer getSigningSerializer(Jlask app) {
    return SigningSerializer.getInstance();
  }

  private String getSessionCookieName(Jlask app) {
    String sessionCookieName = (String) app.getConfig().get("SESSION_COOKIE_NAME");
    sessionCookieName = (sessionCookieName == null) ? "session" : sessionCookieName;
    return sessionCookieName;
  }

  @Override
  public SecureCookieSession openSession(Jlask app, Request request) {
    SigningSerializer serializer = this.getSigningSerializer(app);
    if (serializer == null) return null;
    // val is a encoded string
    String val = request.cookies.get(this.getSessionCookieName(app));

    if (val == null) {
      // empty session
      return new SecureCookieSession(new HashMap<>());
    }

    HashMap<String, Object> data = serializer.loads(val);
    return new SecureCookieSession(data);
  }

  @Override
  public void saveSession(Jlask app, SecureCookieSession session, Response response) {
    String domain = (String) app.getConfig().get("SESSION_COOKIE_DOMAIN"); // might be null
    String path = (String) app.getConfig().get("SESSION_COOKIE_PATH"); // might be null

    // TODO: If the session is modified to be empty, remove the cookie
    // If the session is empty, return without setting the cookie
    if (session == null) {
      return;
    }
    // if (session.accessed) {
    //     response.vary.add("Cookie");
    // }
    if (!this.shouldSetCookie(app, session)) {
      return;
    }
    boolean httponly = this.getCookieHttponly(app);
    boolean secure = this.getCookieSecure(app);
    String samesite = this.getCookieSamesite(app);
    // Time expires = this.getExpirationTime(app);
    int maxAge = this.getCookieMaxAge(app);
    String val = this.getSigningSerializer(app).dumps(session.getDict());
    response.setCookie(
        this.getSessionCookieName(app), val, domain, path, maxAge
        // , expires, httponly, domain, path, secure, samesite
        );
  }
}

/** Java alternative of itsdangerous.URLSafeTimeSerializer */
class SigningSerializer {
  private Signature signature = Signature.getInstance("SHA256WithDSA");
  private SecureRandom secureRandom = new SecureRandom();
  private KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
  private KeyPair keyPair = keyPairGenerator.generateKeyPair();
  private Gson gson = new Gson();
  private Type dictType = new TypeToken<HashMap<String, String>>() {}.getType();

  public SigningSerializer() {
    signature.initSign(keyPair.getPrivate(), secureRandom);
  }

  private static SigningSerializer instance = null;

  public static SigningSerializer getInstance() {
    if (instance == null) {
      instance = new SigningSerializer();
    }
    return instance;
  }

  public HashMap<String, Object> loads(String value) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public String dumps(HashMap<String, Object> dict) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  private String toJsonString(HashMap<String, String> dict) {
    return gson.toJson(dict);
  }

  private HashMap<String, String> fromJsonString(String jsonString) {
    return gson.fromJson(jsonString, dictType);
  }

  private byte[] sign(String target) {
    byte[] data = target.getBytes("UTF-8");
    signature.update(data);
    byte[] digitalSignature = signature.sign();
    return digitalSignature;
  }

  private boolean verify(byte[] digitalSignature) {
    signature.initVerify(keyPair.getPublic());
    return signature.verify(digitalSignature);
  }

  private String toBase64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  private byte[] fromBase64(String base64str) {
    return Base64.getDecoder().decode(base64str);
  }
}
