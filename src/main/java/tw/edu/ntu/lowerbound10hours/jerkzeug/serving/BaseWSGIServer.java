package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;

public class BaseWSGIServer {
  protected boolean multithread = false;
  protected boolean multiprocess = false;

  private InetAddress host;
  private int port;
  private Application app;

  private HttpServer server;
  private HttpHandler handler = new WSGIRequestHandler();

  private boolean shutdownSignal = false;

  public BaseWSGIServer(InetAddress host, int port, Application app) throws Exception {
    this.host = host;
    this.port = port;
    this.app = app;
    //TODO: handle address family
    this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
  }

  public void serveForever() throws Exception {
    //TODO: is this forever?
    this.shutdownSignal = false;
    try {
      this.server.start();
      //TODO: handle keyboard interrupt
    } finally {
      this.server.stop(0);
    }
  }

  public InetAddress getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }

  public void setHandler(HttpHandler handler) {
    this.handler = handler;
  }

  //TODO: 
  //passthrough_errors
  //ssl_context
  //fd
  //
  //log()
  //handle_error()
  //get_request
}
