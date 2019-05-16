package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.util.Map;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import org.eclipse.jetty.server.Server;
import manifold.ext.api.Jailbreak;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class WsgiRequestHandlerTest {
  @Test
  public void testWsgiRequestHandler() throws Exception {
    String name = "localhost";
    InetAddress host = InetAddress.getByName(name);
    int port = 8001;
    Application testApp = new TestApplication();
    Server testServer = new Server(new InetSocketAddress(host, port));
    WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
  }

  @Test
  public void testHandle() throws Exception {
    String name = "localhost";
    InetAddress host = InetAddress.getByName(name);
    int port = 8001;
    Application testApp = new TestApplication();
    Server testServer = new Server(new InetSocketAddress(host, port));
    WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
    handler.handle("hi", null, null, null);
  }

  @Test
  public void testMakeEnviron() throws Exception {
    String name = "localhost";
    InetAddress host = InetAddress.getByName(name);
    int port = 8001;
    Application testApp = new TestApplication();
    @Jailbreak Server testServer = new Server(new InetSocketAddress(host, port));
    @Jailbreak WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
    Map<String, Object> environ = handler.makeEnviron(null, null, null, null);
    int serverPort = (Integer) environ.get("SERVER_PORT");
    String serverName = environ.get("SERVER_NAME").toString();
    assertEquals(serverPort, port);
    assertEquals(serverName, name);
  }
}
