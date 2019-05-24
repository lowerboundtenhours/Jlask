package tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.Rule;

public class Request {
  public Map<String, Object> environ;
  public Rule rule;
  public Map<String, Object> viewArgs;
  public String method;
  public Cookie[] cookies;
  private HttpServletRequest baseRequest;

  /**
   * Wrap javax.servlet.http.HttpServletRequest.
   *
   * @param environ WSGI environ
   */
  public Request(Map<String, Object> environ) {
    this.environ = environ;
    if (environ.containsKey("baseRequest")) {
      this.baseRequest = (HttpServletRequest) environ.get("baseRequest");
      this.method = this.baseRequest.getMethod();
      this.cookies = this.baseRequest.getCookies();
    }
  }

  public String getParameter(String name) {
    return this.baseRequest.getParameter(name);
  }
}
