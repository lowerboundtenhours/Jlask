package tw.edu.ntu.lowerbound10hours.jlask.session;

import java.util.HashMap;

import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class SecureCookieSessionInterface extends SessionInterface {
  public SecureCookieSessionInterface() {};

  private String salt = "cookie-session";

  public Serializer getSigningSerializer(Jlask app) {
    return new Serializer();
  }
  
  private String getSessionCookieName(Jlask app) {
      String sessionCookieName = (String) app.getConfig().get("SESSION_COOKIE_NAME");
      sessionCookieName = (sessionCookieName == null) ? "session" : sessionCookieName;
      return sessionCookieName;
  }
  @Override
  public SecureCookieSession openSession(Jlask app, Request request) {
      Serializer serializer = this.getSigningSerializer(app);
      if (serializer == null) return null;
      // val is a encoded string ?
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
      String domain = (String) app.getConfig().get("SESSION_COOKIE_DOMAIN");  // might be null
      String path = (String) app.getConfig().get("SESSION_COOKIE_PATH");  // might be null

      // TODO: If the session is modified to be empty, remove the cookie
      // If the session is empty, return without setting the cookie
      if (session == null) {
          return;
      }
      if (session.accessed) {
          response.vary.add("Cookie");
      }
      if (!this.shouldSetCookie(app, session)) {
          return;
      }
      boolean httponly = this.getCookieHttponly(app);
      boolean secure = this.getCookieSecure(app);
      String samesite = this.getCookieSamesite(app);
      // Time expires = this.getExpirationTime(app);
      String val = this.getSigningSerializer(app).dumps(session.getDict());
      response.set_cookie(
        this.getSessionCookieName(app), val
        // , expires, httponly, domain, path, secure, samesite
      );

  }
}

/**
 * Java alternative of itsdangerous.URLSafeTimeSerializer
 */
class Serializer {
    public HashMap<String, Object> loads(Object value) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    public String dumps(HashMap<String, Object> dict) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
