package tw.edu.ntu.lowerbound10hours.jlask.session;

import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;
public class SessionInterface {
  public SessionInterface() {}

  public Session open_session(Jlask app, Request request) {
    return new Session();
  }

  public void save_session(Jlask app, Session session, Response response) {
    int a = 123;
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
         return (boolean) app.getConfig().get("SESSION_COOKIE_HTTPONLY");
     }
     public boolean getCookieSecure(Jlask app) {
         return (boolean) app.getConfig().get("SESSION_COOKIE_SECURE");
     }
     public String getCookieSamesite(Jlask app) {
         return (String) app.getConfig().get("SESSION_COOKIE_SAMESITE");
     }
     // public Time getExpirationTime(Jlask app, Session session) {};
     public boolean shouldSetCookie(Jlask app, Session session) {};
     
}
