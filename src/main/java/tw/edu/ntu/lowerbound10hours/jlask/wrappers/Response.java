package tw.edu.ntu.lowerbound10hours.jlask.wrappers;

import java.util.Map;

public class Response {

  private String body;
  private Integer status = null;
  private Map<String, String> headers = null;

  public Response(String body) {
    this.body = body;
  }

  public Response(String body, int status) {
    this.body = body;
    this.status = status;
  }

  public Response(String body, int status, Map<String, String> headers) {
    this.body = body;
    this.status = status;
    this.headers = headers;
  }

  public String getBody() {
    return this.body;
  }
}
