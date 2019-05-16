package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.net.InetAddress;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class BaseWsgiServerTest {
  private static final String name = "localhost";
  private static final int port = 8001;
  private static Application testApp = new TestApplication();

  @Test
  public void testBaseWsgiServer() throws Exception {
    InetAddress host = InetAddress.getByName(name);
    BaseWsgiServer server = new BaseWsgiServer(host, port, testApp);
    // Thread.currentThread().interrupt();
    // server.serveForever();
  }
}
