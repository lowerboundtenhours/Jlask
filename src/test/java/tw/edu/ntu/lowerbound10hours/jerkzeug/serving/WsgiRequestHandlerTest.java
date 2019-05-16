package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.assertEquals;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import manifold.ext.api.Jailbreak;
import org.eclipse.jetty.server.Server;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class WsgiRequestHandlerTest {
  private static final String name = "localhost";
  private static final int port = 8001;
  private static Application testApp = new TestApplication();

  @Test
  public void testWsgiRequestHandler() throws Exception {
    Application testApp = new TestApplication();
    InetAddress host = InetAddress.getByName(name);
    Server testServer = new Server(new InetSocketAddress(host, port));
    WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
  }

  @Test
  public void testHandle() throws Exception {
    Application testApp = new TestApplication();
    InetAddress host = InetAddress.getByName(name);
    Server testServer = new Server(new InetSocketAddress(host, port));
    WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
    handler.handle("hi", null, null, null);
  }

  @Test
  public void testMakeEnviron() throws Exception {
    InetAddress host = InetAddress.getByName(name);
    Server testServer = new Server(new InetSocketAddress(host, port));
    @Jailbreak WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
    Map<String, Object> environ = handler.makeEnviron(null, null, null, null);
    int serverPort = (int) environ.get("SERVER_PORT");
    String serverName = environ.get("SERVER_NAME").toString();
    assertEquals(serverPort, port);
    assertEquals(serverName, name);
  }
}
