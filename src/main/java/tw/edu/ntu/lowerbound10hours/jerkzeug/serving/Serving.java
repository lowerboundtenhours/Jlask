package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.net.InetAddress;

public class Serving {
  public static BaseWSGIServer makeServer(InetAddress host, int port) throws Exception {
    BaseWSGIServer server = new BaseWSGIServer(host, port);
    return server;
  }
}
