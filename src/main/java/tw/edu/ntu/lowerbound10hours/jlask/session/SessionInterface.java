package tw.edu.ntu.lowerbound10hours.jlask.session;

import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class SessionInterface {
  public SessionInterface() {}

  public SecureCookieSession openSession(Jlask app, Request request) {
    throw new UnsupportedOperationException("Pleaes implement this function in your subclass.");
  }

  public void saveSession(Jlask app, SecureCookieSession session, Response response) {
    throw new UnsupportedOperationException("Pleaes implement this function in your subclass.");
  }

  public NullSession maksNullSession() {
    return new NullSession();
  }

  public boolean isNullSession(Object obj) {
    return (obj instanceof NullSession);
  }

  public String getCookieDomain(Jlask app) {
    String ret = (String) app.getConfig().get("SESSION_COOKIE_DOMAIN");
    if (ret == null) {
      ret = (String) app.getConfig().get("SERVER_NAME");
    }
    if (ret == null) {
      ret = "localhost";
    }
    // Chop off the port which is usually not supported by browsers
    ret = ret.split(":")[0];
    // Remove any leading '.' since we'll add that later
    ret = ret.replaceAll("^\\.+", "");

    // TODO: test if '.' in ret -> warning
    // TODO: test if ret is an ip -> warning
    // TODO: if this is not an ip and app is mounted at the root, allow subdomain
    // matching by adding a '.' prefix

    app.getConfig().put("SESSION_COOKIE_DOMAIN", ret);
    return ret;
  }

  public String getCookiePath(Jlask app) {
    String ret = (String) app.getConfig().get("SESSION_COOKIE_PATH");
    if (ret == null) {
      ret = (String) app.getConfig().get("APPLICATION_ROOT");
    }
    return ret;
  }

  public boolean getCookieHttponly(Jlask app) {
    Boolean ret = (Boolean) app.getConfig().get("SESSION_COOKIE_HTTPONLY");
    ret = (ret == null) ? true : ret;
    return ret;
  }

  public boolean getCookieSecure(Jlask app) {
    Boolean ret = (Boolean) app.getConfig().get("SESSION_COOKIE_SECURE");
    ret = (ret == null) ? false : ret;
    return ret;
  }

  public String getCookieSamesite(Jlask app) {
    return (String) app.getConfig().get("SESSION_COOKIE_SAMESITE");
  }

  public Integer getCookieMaxAge(Jlask app) {
    Integer ret = (Integer) app.getConfig().get("SESSION_COOKIE_MAXAGE");
    ret = (ret == null) ? 3600 * 24 : ret;
    return ret;
  }
  // public Time getExpirationTime(Jlask app, Session session) {};
  public boolean shouldSetCookie(Jlask app, SecureCookieSession session) {
    if (session.modified) {
      return true;
    }
    if (this.getSessionRefresionEachRequest(app)) {
      return true;
    }
    return false;
  }

  private boolean getSessionRefresionEachRequest(Jlask app) {
    Boolean ret = (Boolean) app.getConfig().get("SESSION_REFRESH_EACH_REQUEST");
    ret = (ret == null) ? false : ret;
    return ret;
  }
}
