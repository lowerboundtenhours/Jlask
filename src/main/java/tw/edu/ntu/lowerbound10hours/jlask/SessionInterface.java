public class SessionInterface {
    public SessionInterface() {
    }

    public Session open_session (Jlask app, Request request) {
        return new Session();
    }

    public void save_session(Jlask app, Session session, Response response) {
    
    }
}
