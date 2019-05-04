package tw.edu.ntu.lowerbound10hours.jerkzeug;

import com.sun.net.httpserver.HttpServer;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class BaseWSGIServer {
  private InetAddress host;
  private int port;
  private HttpServer server;

  public BaseWSGIServer(InetAddress host, int port) throws Exception {
    this.host = host;
    this.port = port;
    this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
  }

  public InetAddress getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }
}
