package tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpHeader;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

public class Response {
  private Integer status = null;
  private Map<String, String> headers = null;
  private List<String> response;
  private HttpServletResponse baseResponse;

  public Response(String rv, Map<String, Object> environ) {
    this.response = new ArrayList<>();
    this.response.add(rv);
    this.baseResponse = (HttpServletResponse) environ.get("baseResponse");
  }

  public Response(List<String> rv, Map<String, Object> environ) {
    this.response = rv;
    this.baseResponse = (HttpServletResponse) environ.get("baseResponse");
  }

  public void setCookie(String key, String value, String domain, String path, int maxAge) {
    Cookie newCookie = new Cookie(key, value);
    newCookie.setDomain(domain);
    newCookie.setPath(path);
    newCookie.setMaxAge(maxAge);
    this.baseResponse.addCookie(newCookie);
  }

  public void deleteCookie(String key) {
    Cookie newCookie = new Cookie(key, "");
    newCookie.setMaxAge(0);
    this.baseResponse.addCookie(newCookie);
  }

  private int getStatus(Map<String, Object> environ) {
    // TODO: implement return status
    return 200;
  }

  private ArrayList<HttpHeader> getResponseHeaders(Map<String, Object> environ) {
    // TODO: implement jetty HttpHeader converter
    return new ArrayList<>();
  }

  private ApplicationIter<String> getApplicationIter(Map<String, Object> environ) {
    return new ApplicationIter<String>(this.response);
  }

  /**
   * Process this response as WSGI application.
   *
   * @param environ WSGI environ
   * @param startResponse WSGI startResponse
   * @return ApplicationIter
   */
  public ApplicationIter<String> call(Map<String, Object> environ, StartResponse startResponse) {
    int status = this.getStatus(environ);
    ArrayList<HttpHeader> headers = this.getResponseHeaders(environ);
    ApplicationIter<String> iter = this.getApplicationIter(environ);
    startResponse.startResponse(status, headers, false);
    return iter;
  }
}
