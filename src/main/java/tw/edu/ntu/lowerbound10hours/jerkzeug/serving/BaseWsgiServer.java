package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;

/**
 * A WSGI server based on Jetty.
 */
public class BaseWsgiServer {
  protected boolean multithread = false;
  protected boolean multiprocess = false;

  private InetAddress host;
  private int port;
  private Application app;

  private Server server;
  private AbstractHandler handler;

  private boolean shutdownSignal = false;

  /**
   * Initialize the server given address and application.
   */
  public BaseWsgiServer(InetAddress host, int port, Application app) throws Exception {
    this.host = host;
    this.port = port;
    this.app = app;
    // TODO: handle address family
    this.server = new Server(new InetSocketAddress(host, port));
    this.handler = new WsgiRequestHandler(app);
    this.server.setHandler(this.handler);
    // this.server.createContext("/test", new MyHandler());
    // this.server.setExecutor(null); // creates a default executor
  }

  /**
   * Start the jetty server and listen to requests.
   * Note that this will cause an infinte loop that could only be terminated by ctrl+c.
   */
  public void serveForever() throws Exception {
    this.shutdownSignal = false;
    try {
      this.server.start();
      // Run forever; terminated by a keyboard interrupt
      while (!Thread.currentThread().isInterrupted()) {
        Thread.sleep(100);
      }
    } catch (InterruptedException e) {
      System.out.println("Keyboard interrupt; server stops.");
    } finally {
      this.server.join();
    }
  }

  public InetAddress getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }

  public void setHandler(AbstractHandler handler) {
    this.handler = handler;
  }

  static class TestHandler implements HttpHandler {
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
