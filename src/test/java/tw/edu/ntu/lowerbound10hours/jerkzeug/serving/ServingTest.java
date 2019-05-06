package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.*;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class ServingTest {
  @org.testng.annotations.Test
  public void test() throws Exception {
    assertEquals(3, 3);
  }

  @org.testng.annotations.Test
  public void testMakeServer() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    BaseWSGIServer server = Serving.makeServer(host, port, testApp);
    assertEquals(server.getPort(), port);
    assertEquals(server.getHost(), host);
  }

  @org.testng.annotations.Test(enabled = false)
  public void testRunSimple() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8002;
    Application testApp = new TestApplication();
    Serving.runSimple(host, port, testApp);
  }
}
