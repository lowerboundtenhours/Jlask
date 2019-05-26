package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;

public class WsgiRequestHandler extends AbstractHandler {
  private Application app;
  private Server server;
  private Map<String, Object> environ;
  private Write write;
  private StartResponse startResponse;
  // TODO: check headersSet and headersSent type
  private ArrayList<Object> headersSet;
  private ArrayList<Object> headersSent;

  public WsgiRequestHandler(Application app, Server server) {
    this.app = app;
    this.server = server;
  }

  /**
   * The entry point when Jetty get a request. This function will finally call app.call(), writen by
   * the user
   */
  public void handle(
      String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    //
    // Handles a request ignoring dropped connections.
    //

    // TODO: Handle connection timeout
    // TODO: Handle other exceptions
    // TODO: Handle server shutdown
    try {
      this.handleOneRequest(target, baseRequest, request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleOneRequest(
      String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    //
    // Handle a single HTTP request.
    //

    // TODO: Check if request is parsed by Jetty
    this.runWsgi(target, baseRequest, request, response);
  }

  private void runWsgi(
      String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // TODO: handle 100-continue in header
    this.environ = this.makeEnviron(target, baseRequest, request, response);
    this.headersSet = new ArrayList<Object>();
    this.headersSent = new ArrayList<Object>();

    this.write = new Write(response.getWriter(), this);
    this.startResponse = new StartResponse(this.write, this);

    try {
      // TODO: check where to put these three lines for Jetty
      response.setContentType("text/html; charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
      baseRequest.setHandled(true);
      this.execute(this.app);
    } catch (Exception e) {
      // TODO: handle connection drop
      // TODO: handle other exceptions
    }
  }

  private Map<String, Object> makeEnviron(
      String target,
      Request baseRequest,
      HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> environ = new HashMap<String, Object>();
    String ipAddress = request.getHeader("X-FORWARDED-FOR");  
    if (ipAddress == null) {  
        ipAddress = request.getRemoteAddr();  
    }
    // TODO: setup environ
    environ.put("SERVER_PORT", ((ServerConnector) this.server.getConnectors()[0]).getPort());
    environ.put("SERVER_NAME", this.server.getURI().getHost());
    environ.put("SERVER_URI", this.server.getURI());
    environ.put("REMOTE_ADDR", ipAddress);
    environ.put("PATH_INFO", request.getPathInfo());
    environ.put("target", target);
    environ.put("baseRequest", baseRequest);
    environ.put("request", request);
    environ.put("response", response);
    return environ;
  }

  private void execute(Application app) {
    // TODO[important]: ApplicationIter type is unknown
    ApplicationIter<String> applicationIter = app.call(this.environ, this.startResponse);
    try {
      applicationIter.forEach(
          (data) -> {
            this.write.write(data);
          });
      if (this.headersSet.size() == 0) {
        // TODO: this is b"", not ""
        this.write.write("");
      }

    } finally {
      // TODO: close applicationIter
    }
  }

  public ArrayList<Object> getHeadersSet() {
    return this.headersSet;
  }

  public ArrayList<Object> getHeadersSent() {
    return this.headersSent;
  }

  public void addHeadersSet(Object o) {
    this.headersSet.add(o);
  }

  public void addHeadersSent(Object o) {
    this.headersSent.add(o);
  }

  public boolean checkHeadersSet() {
    return this.headersSet.size() != 0;
  }

  public boolean checkHeadersSent() {
    return this.headersSent.size() != 0;
  }
}
