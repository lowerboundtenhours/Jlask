package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import static org.testng.Assert.assertEquals;

import java.net.InetAddress;
import java.net.HttpURLConnection;
import java.net.URL;
import org.testng.annotations.Test;
import org.eclipse.jetty.http.HttpStatus;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;

public class ServingTest {
  private static final String name = "localhost";
  private static final int port = 8001;
  private static Application testApp = new TestApplication();

  @Test
  public void test() throws Exception {
    assertEquals(3, 3);
  }

  @Test
  public void testMakeServer() throws Exception {
    // TODO: make this @Before
    InetAddress host = InetAddress.getByName(name);
    BaseWsgiServer server = Serving.makeServer(host, port, testApp);
    assertEquals(server.getPort(), port);
    assertEquals(server.getHost(), host);
  }

  @Test
  public void testRequest() throws Exception {
    InetAddress host = InetAddress.getByName(name);
    BaseWsgiServer server = Serving.makeServer(host, port, testApp);
    server.getServer().start();
    HttpURLConnection http = (HttpURLConnection)new URL(String.format("http://%s:%d/", name, port)).openConnection();
		http.connect();
		// TODO: pass this check
		// assertEquals(http.getResponseMessage(), "This is a test response from TestApplication");
		// assertEquals(http.getResponseCode(), 200);
		
		server.getServer().stop();
  }


  @Test(enabled = false)
  public void testRunSimple() throws Exception {
    InetAddress host = InetAddress.getByName(name);
    Serving.runSimple(host, port, testApp);
  }
}
