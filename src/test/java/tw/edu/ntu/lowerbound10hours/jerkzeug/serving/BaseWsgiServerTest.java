package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.net.InetAddress;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class BaseWsgiServerTest {
  @Test
  public void testBaseWsgiServer() throws Exception {
    String name = "localhost";
    InetAddress host = InetAddress.getByName(name);
    int port = 8001;
    Application testApp = new TestApplication();
    BaseWsgiServer server = new BaseWsgiServer(host, port, testApp);
    // Thread.currentThread().interrupt();
    // server.serveForever();
  }
}
