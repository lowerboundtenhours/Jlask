package tw.edu.ntu.lowerbound10hours.jlask.session;

public class SecureCookieSessionInterface extends SessionInterface {
  public SecureCookieSessionInterface() {}
}
    private String salt = "cookie-session";

    public Serializer getSigningSerializer(App app) {
        return null;
    }
    public SecureCookieSession openSession(App app, Request request) {
        return null;
    }
    public void saveSession(App app, SecureCookieSession session, Response response) {

    }
}
