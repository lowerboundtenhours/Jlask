package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.*;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class BaseWSGIServerTest {
  @org.testng.annotations.Test
  public void testBaseWSGIServer() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    BaseWSGIServer server = new BaseWSGIServer(host, port, testApp);
    // Thread.currentThread().interrupt();
    // server.serveForever();
  }
}
