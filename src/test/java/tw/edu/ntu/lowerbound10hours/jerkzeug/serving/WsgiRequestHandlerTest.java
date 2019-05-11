package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class WsgiRequestHandlerTest {
  @Test
  public void testWsgiRequestHandler() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    WsgiRequestHandler handler = new WsgiRequestHandler(testApp);
  }

  @Test
  public void testHandle() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    WsgiRequestHandler handler = new WsgiRequestHandler(testApp);
    handler.handle("hi", null, null, null);
  }
}
