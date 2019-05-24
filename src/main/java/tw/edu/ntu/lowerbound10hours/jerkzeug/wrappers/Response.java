package tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers;

import java.io.IOException;
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

  /**
   * Wrap javax.servlet.http.HttpServletResponse.
   *
   * @param rv return body as String
   * @param environ WSGI environ
   * @param status HTTP status
   */
  public Response(String rv, Map<String, Object> environ, int status) {
    this.response = new ArrayList<>();
    this.response.add(rv);
    this.baseResponse = (HttpServletResponse) environ.get("baseResponse");
    this.status = status;
  }

  /**
   * Wrap javax.servlet.http.HttpServletResponse.
   *
   * @param rv return body as List
   * @param environ WSGI environ
   * @param status HTTP status
   */
  public Response(List<String> rv, Map<String, Object> environ, int status) {
    this.response = rv;
    this.baseResponse = (HttpServletResponse) environ.get("baseResponse");
    this.status = status;
  }

  /**
   * Add a cookie to response.
   *
   * @param key cookie key
   * @param value cookie value
   * @param domain cookie domain
   * @param path cookie path
   * @param maxAge cookie maxAge, in seconds
   */
  public void setCookie(String key, String value, String domain, String path, int maxAge) {
    Cookie newCookie = new Cookie(key, value);
    newCookie.setDomain(domain);
    newCookie.setPath(path);
    newCookie.setMaxAge(maxAge);
    this.baseResponse.addCookie(newCookie);
  }

  /**
   * Remove a cookie from response.
   *
   * @param key cookie key
   */
  public void deleteCookie(String key) {
    Cookie newCookie = new Cookie(key, "");
    newCookie.setMaxAge(0);
    this.baseResponse.addCookie(newCookie);
  }

  /**
   * Set redirect in response.
   *
   * @param location location to redirect
   */
  public void sendRedirect(String location) {
    try {
      this.baseResponse.sendRedirect(location);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getStatus() {
    return this.status;
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
    int status = this.getStatus();
    ArrayList<HttpHeader> headers = this.getResponseHeaders(environ);
    ApplicationIter<String> iter = this.getApplicationIter(environ);
    startResponse.startResponse(status, headers, false);
    return iter;
  }
}
