package tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.http.HttpHeader;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

public class Response {
  private List<String> response;

  public Response(String rv) {
    this.response = new ArrayList<>();
    this.response.add(rv);
  }

  public Response(List<String> rv) {
    this.response = rv;
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
    return new ApplicationIter<>(this.response);
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
