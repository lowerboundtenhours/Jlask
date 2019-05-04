package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import com.sun.net.httpserver.HttpServer;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class BaseWSGIServer {
  protected boolean multithread = false;
  protected boolean multiprocess = false;

  private InetAddress host;
  private int port;
  private HttpServer server;

  private boolean shutdownSignal = false;

  public BaseWSGIServer(InetAddress host, int port) throws Exception {
    this.host = host;
    this.port = port;
    //TODO: handle address family
    this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
  }

  public void serveForever() throws Exception {
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
}
