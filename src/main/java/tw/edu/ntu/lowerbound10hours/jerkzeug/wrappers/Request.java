package tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.Rule;

public class Request {
  public Map<String, Object> environ;
  public Rule rule;
  public Map<String, Object> viewArgs;
  public String method;
  public Map<String, Cookie> cookies;
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
      this.cookies = new HashMap<String, Cookie>();
      if (this.baseRequest.getCookies() != null) {
        for (Cookie cookie : this.baseRequest.getCookies()) {
          this.cookies.put(cookie.getName(), cookie);
        }
      }
    }
  }

  public String getParameter(String name) {
    return this.baseRequest.getParameter(name);
  }
}
