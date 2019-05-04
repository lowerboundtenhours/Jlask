package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.*;

import java.net.InetAddress;

public class ServingTest {
  @org.testng.annotations.Test
  public void test() throws Exception {
    assertEquals(3, 3);
  }

  @org.testng.annotations.Test
  public void testMakeServer() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    BaseWSGIServer server = Serving.makeServer(host, port);
    assertEquals(server.getPort(), port);
    assertEquals(server.getHost(), host);
  }

  @org.testng.annotations.Test
  public void testServeForever() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8002;
    BaseWSGIServer server = Serving.makeServer(host, port);
    server.serveForever();
  }
}
