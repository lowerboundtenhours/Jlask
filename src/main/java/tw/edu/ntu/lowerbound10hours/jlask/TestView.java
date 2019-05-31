package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;

public class TestView extends View {
  @Override
  public String dispatchRequest(Map<String, Object> args) {
    SecureCookieSession session = (SecureCookieSession) Global.session();
    if (!session.contains("hello")) {
      System.out.println("Session does not contain key 'hello'");
      session.set("hello", "heyyy");
    } else {
      System.out.println("Get session key: " + session.get("hello"));
    }
    return "foo";
  }
}
