package tw.edu.ntu.lowerbound10hours.example;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.BaseWsgiServer;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;
import tw.edu.ntu.lowerbound10hours.jlask.Global;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;

public class CreateViewTest {
  private static final String name = "localhost";
  private static final int port = 8001;

  @Test
  public void testGetDispatchRequest() throws Exception {
    Jlask application = new Jlask();
    Hibernate db = new Hibernate();
    TemplateEngine templateEngine = new TemplateEngine();

    // initilize database
    db.initApp(application);
    application.addUrlRule("/<int:id>", "index", new CreateView(templateEngine, db));

    InetAddress host = InetAddress.getByName(name);
    BaseWsgiServer server = Serving.makeServer(host, port, application);
    server.getServer().start();
    SecureCookieSession session = (SecureCookieSession) Global.session();
    session.set("username", "t");
    session.set("id", "1");
    HttpURLConnection http =
        (HttpURLConnection) new URL(String.format("http://%s:%d/1", name, port)).openConnection();
    http.setRequestMethod("GET");
    http.setUseCaches(false);
    http.setAllowUserInteraction(false);
    http.setRequestProperty("User-Agent", "Mozilla/5.0");
    http.connect();
    // TODO: pass this check
    // assertEquals(http.getResponseMessage(), "This is a test response from TestApplication");
    // assertEquals(http.getResponseCode(), 200);
    http.connect();
    int status = http.getResponseCode();

    server.getServer().stop();
  }

  @Test
  void testPostDispatchRequest() throws Exception {
    Jlask application = new Jlask();
    Hibernate db = new Hibernate();
    TemplateEngine templateEngine = new TemplateEngine();

    // initilize database
    db.initApp(application);
    application.addUrlRule("/<int:id>", "index", new CreateView(templateEngine, db));

    InetAddress host = InetAddress.getByName(name);
    BaseWsgiServer server = Serving.makeServer(host, port, application);
    server.getServer().start();
    SecureCookieSession session = (SecureCookieSession) Global.session();
    session.set("username", "t");
    session.set("id", "1");
    HttpURLConnection http =
        (HttpURLConnection) new URL(String.format("http://%s:%d/1", name, port)).openConnection();
    http.setRequestMethod("POST");
    http.setDoOutput(true);
    http.setDoInput(true);
    http.setUseCaches(false);
    http.setRequestProperty("User-Agent", "Mozilla/5.0");
    http.connect();
    // TODO: pass this check
    // assertEquals(http.getResponseMessage(), "This is a test response from TestApplication");
    // assertEquals(http.getResponseCode(), 200);
    http.connect();
    int status = http.getResponseCode();

    server.getServer().stop();
  }
}
