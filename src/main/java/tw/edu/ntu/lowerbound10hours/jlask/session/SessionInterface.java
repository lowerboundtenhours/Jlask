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

  public NullSession makeNullSession() {
    return new NullSession();
  }

  public boolean isNullSession(Object obj) {
    return (obj instanceof NullSession);
  }

  protected String getCookieDomain(Jlask app) {
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

  protected String getCookiePath(Jlask app) {
    String ret = (String) app.getConfig().get("SESSION_COOKIE_PATH");
    if (ret == null) {
      ret = (String) app.getConfig().get("APPLICATION_ROOT");
    }
    return ret;
  }

  protected boolean getCookieHttponly(Jlask app) {
    String retString = app.getConfig().get("SESSION_COOKIE_HTTPONLY");
    Boolean ret = (retString == null) ? true : Boolean.valueOf(retString);
    return ret;
  }

  protected boolean getCookieSecure(Jlask app) {
    String retString = app.getConfig().get("SESSION_COOKIE_SECURE");
    Boolean ret = (retString == null) ? false : Boolean.valueOf(retString);
    return ret;
  }

  protected String getCookieSamesite(Jlask app) {
    return (String) app.getConfig().get("SESSION_COOKIE_SAMESITE");
  }

  protected Integer getCookieMaxAge(Jlask app) {
    String retString = app.getConfig().get("SESSION_COOKIE_MAXAGE");
    Integer ret = (retString == null) ? 3600 * 24 : Integer.valueOf(retString);
    return ret;
  }

  // protected Time getExpirationTime(Jlask app, Session session) {};
  protected boolean shouldSetCookie(Jlask app, SecureCookieSession session) {
    if (session.modified) {
      return true;
    }
    if (this.getSessionRefresionEachRequest(app)) {
      return true;
    }
    return false;
  }

  protected boolean getSessionRefresionEachRequest(Jlask app) {
    String retString = app.getConfig().get("SESSION_REFRESH_EACH_REQUEST");
    Boolean ret = (retString == null) ? false : Boolean.valueOf(retString);
    return ret;
  }
}
