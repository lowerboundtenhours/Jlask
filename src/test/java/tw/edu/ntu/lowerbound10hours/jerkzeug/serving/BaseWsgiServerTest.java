package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.net.InetAddress;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class BaseWsgiServerTest {
  @Test
  public void testBaseWsgiServer() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    BaseWsgiServer server = new BaseWsgiServer(host, port, testApp);
    // Thread.currentThread().interrupt();
    // server.serveForever();
  }
}
