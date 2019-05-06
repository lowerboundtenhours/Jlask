package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
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
    // TODO: handle address family
    this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
    this.server.createContext("/test", new MyHandler());
    this.server.setExecutor(null); // creates a default executor
  }

  public void serveForever() throws Exception {
    System.out.printf("Server started on %s:%s\n", host, port);
    this.shutdownSignal = false;
    try {
      this.server.start();
      while (!Thread.currentThread().isInterrupted()) Thread.sleep(100);
      // TODO: handle keyboard interrupt
    } catch (InterruptedException e) {

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

  static class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
      String response = t.getRequestURI().getPath();
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  // TODO:
  // passthrough_errors
  // ssl_context
  // fd
  //
  // log()
  // handle_error()
  // get_request
}
