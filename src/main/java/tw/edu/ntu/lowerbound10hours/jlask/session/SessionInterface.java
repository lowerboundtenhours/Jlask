package tw.edu.ntu.lowerbound10hours.jlask.session;

import tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper.Request;
import tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper.Response;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;

public class SessionInterface {
  public SessionInterface() {}

  public Session open_session(Jlask app, Request request) {
    return new Session();
  }

  public void save_session(Jlask app, Session session, Response response) {}
}
