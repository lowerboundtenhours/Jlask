package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.assertEquals;

import java.net.InetAddress;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class ServingTest {
  @Test
  public void test() throws Exception {
    assertEquals(3, 3);
  }

  @Test
  public void testMakeServer() throws Exception {
    String name = "localhost";
    InetAddress host = InetAddress.getByName(name);
    int port = 8001;
    Application testApp = new TestApplication();
    BaseWsgiServer server = Serving.makeServer(host, port, testApp);
    assertEquals(server.getPort(), port);
    assertEquals(server.getHost(), host);
  }

  @Test(enabled = false)
  public void testRunSimple() throws Exception {
    String name = "localhost";
    InetAddress host = InetAddress.getByName(name);
    int port = 8002;
    Application testApp = new TestApplication();
    Serving.runSimple(host, port, testApp);
  }
}
