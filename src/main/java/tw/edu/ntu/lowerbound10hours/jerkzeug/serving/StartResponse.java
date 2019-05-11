package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.util.ArrayList;
import org.eclipse.jetty.http.HttpHeader;

public class StartResponse {
  private Write write;
  private WsgiRequestHandler handler;

  public StartResponse(Write write, WsgiRequestHandler handler) {
    this.write = write;
    this.handler = handler;
  }

  /** Model the start_response() inner function in Werkzeug serving.py run_wsgi() */
  public Write startResponse(
      Integer status, ArrayList<HttpHeader> responseHeaders, Boolean excInfo) {
    if (excInfo) {
      try {
        if (this.handler.checkHeadersSent()) {
          // TODO: reraise excInfo
        }
      } finally {
        // TODO: set excInfo to None
      }
    } else {
      assert this.handler.checkHeadersSet();
    }
    this.handler.addHeadersSet(status);
    this.handler.addHeadersSet(responseHeaders);

    return this.write;
  }

  public WsgiRequestHandler getHandler() {
    return this.handler;
  }

  public Write getWrite() {
    return this.write;
  }
}
