package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import static org.testng.Assert.assertEquals;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import manifold.ext.api.Jailbreak;
import org.eclipse.jetty.server.Server;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.WsgiRequestHandler;

public class MapTest {

  public Map exampleMap() {
    ArrayList<RuleFactory> rules = new ArrayList<>();
    rules.add(new Rule("/", "index"));
    rules.add(new Rule("/downloads/", "downloads/index"));
    rules.add(new Rule("/downloads/<int:id>", "downloads/show"));
    return new Map(rules);
  }

  public java.util.Map<String, Object> exampleEnvironment() {
    int port = 8001;
    String name = "localhost";
    try {
      InetAddress host = InetAddress.getByName(name);
      Application testApp = new TestApplication();
      Server testServer = new Server(new InetSocketAddress(host, port));
      @Jailbreak WsgiRequestHandler handler = new WsgiRequestHandler(testApp, testServer);
      java.util.Map<String, Object> environ = handler.makeEnviron(null, null, null, null);
      return environ;
    } catch (Exception e) {
      return null;
    }
  }

  @Test
  public void testMap() {
    Map map = exampleMap();
  }

  @Test
  public void testBindToEnviron() {
    Map map = exampleMap();
    java.util.Map<String, Object> environ = exampleEnvironment();
    MapAdapter urls = map.bindToEnvironment(environ, null, null);

    Pair<String, HashMap<String, Object>> result;
    result = urls.match("/downloads/12");
    assertEquals(result.getKey(), "downloads/show");
    assertEquals(result.getValue().size(), 1);
    assertEquals((Integer) result.getValue().get("id"), new Integer(12));
  }
}
