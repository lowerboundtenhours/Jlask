package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.util.ArrayList;
import org.eclipse.jetty.http.HttpHeader;

public class StartResponse {
  private Write write;
  private WSGIRequestHandler handler;

  public StartResponse(Write write, WSGIRequestHandler handler) {
    this.write = write;
    this.handler = handler;
  }

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

  public WSGIRequestHandler getHandler() {
    return this.handler;
  }

  public Write getWrite() {
    return this.write;
  }
}
