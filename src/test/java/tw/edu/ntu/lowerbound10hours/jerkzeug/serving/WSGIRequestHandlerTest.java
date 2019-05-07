package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.*;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class WSGIRequestHandlerTest {
  @org.testng.annotations.Test
  public void testWSGIRequestHandler() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    WSGIRequestHandler handler = new WSGIRequestHandler(testApp);
  }

  @org.testng.annotations.Test
  public void testHandle() throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8001;
    Application testApp = new TestApplication();
    WSGIRequestHandler handler = new WSGIRequestHandler(testApp);
    handler.handle("hi", null, null, null);
  }
}
