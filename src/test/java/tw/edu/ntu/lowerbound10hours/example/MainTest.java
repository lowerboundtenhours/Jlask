package tw.edu.ntu.lowerbound10hours.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.BaseWsgiServer;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;

public class MainTest {
  private static final String name = "localhost";
  private static final int port = 8015;

  @Test
  public void testBlog() throws Exception {
    Jlask application = new Jlask();
    Hibernate db = new Hibernate();
    TemplateEngine templateEngine = new TemplateEngine();

    // initilize database
    db.initApp(application);

    // addUrlRule
    application.addUrlRule("/", "index", new BlogView(templateEngine, db));
    application.addUrlRule("/create", "create", new CreateView(templateEngine, db));
    application.addUrlRule("/register", "register", new RegisterView(templateEngine, db));
    application.addUrlRule("/login", "login", new LoginView(templateEngine, db));
    application.addUrlRule("/update/<int:id>", "update", new UpdateView(templateEngine, db));
    application.addUrlRule("/delete/<int:id>", "delete", new DeleteView(templateEngine, db));
    application.addUrlRule("/logout", "logout", new LogOutView(templateEngine));

    InetAddress host = InetAddress.getByName(name);
    BaseWsgiServer server = Serving.makeServer(host, port, application);
    server.getServer().start();
    String baseUrl = String.format("http://%s:%d", name, port);

    // GET register
    if (!doGet(String.format("%s/register", baseUrl))) {
      System.err.println("Fail Get register");
      return;
    }
    // POST register with username="t" password = "1"
    if (!doPost(
        String.format("%s/register", baseUrl),
        String.format("username=%s&password=%s", "t", "1"))) {
      System.err.println("Fail post register");
      return;
    }

    // GET login
    if (!doGet(String.format("%s/login", baseUrl))) {
      System.err.println("Fail get login");
      return;
    }
    // POST login with username="t" password = "1"
    if (!doPost(
        String.format("%s/login", baseUrl), String.format("username=%s&password=%s", "t", "1"))) {
      System.err.println("Fail post login");
      return;
    }

    // GET create
    if (!doGet(String.format("%s/create", baseUrl))) {
      System.err.println("Fail get create");
    }
    // POST create
    if (!doPost(
        String.format("%s/create", baseUrl), String.format("title=%s&body=%s", "Hello", "World"))) {
      System.err.println("Fail Post register");
      return;
    }

    // GET blog index
    if (!doGet(String.format("%s/", baseUrl))) {
      System.err.println("Fail get /");
    }

    // GET Update index
    if (!doGet(String.format("%s/update/%s", baseUrl, "1"))) {
      System.err.println("Fail get /update/1");
    }
    // POST update
    if (!doPost(
        String.format("%s/update/%s", baseUrl, "1"),
        String.format("title=%s&body=%s", "Hello!", "World!"))) {
      System.err.println("Fail Post /update/1");
      return;
    }

    // GET delete
    if (!doGet(String.format("%s/delete/%s", baseUrl, "1"))) {
      System.err.println("Fail get /delete/1");
    }

    // GET logout
    if (!doGet(String.format("%s/logout", baseUrl))) {
      System.err.println("Fail get /logout");
    }

    server.getServer().stop();
  }

  /** post url. */
  public boolean doPost(String sourceUrl, String data) {
    boolean doSuccess = false;
    java.io.BufferedWriter wr = null;
    try {
      URL url = new URL(sourceUrl);
      HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
      urlConn.setDoOutput(true);
      urlConn.setDoInput(true);
      ((HttpURLConnection) urlConn).setRequestMethod("POST");
      urlConn.setUseCaches(false);
      urlConn.setAllowUserInteraction(true);
      HttpURLConnection.setFollowRedirects(true);
      urlConn.setInstanceFollowRedirects(true);
      urlConn.setRequestProperty("User-agent", "Mozilla/5.0");
      urlConn.setRequestProperty(
          "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      urlConn.setRequestProperty("Accept-Language", "zh-tw,en-us;q=0.7,en;q=0.3");
      urlConn.setRequestProperty("Accept-Charse", "Big5,utf-8;q=0.7,*;q=0.7");
      urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      urlConn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
      java.io.DataOutputStream dos = new java.io.DataOutputStream(urlConn.getOutputStream());
      dos.writeBytes(data);
    } catch (java.io.IOException e) {
      doSuccess = false;
      System.out.print(e);
    } finally {
      if (wr != null) {
        try {
          wr.close();
        } catch (java.io.IOException ex) {
          System.out.print(ex);
        }
        wr = null;
      }
    }
    return doSuccess;
  }

  /** get url. */
  public boolean doGet(String sourceUrl) {
    boolean doSuccess = false;
    BufferedReader in = null;
    try {
      URL url = new URL(sourceUrl);
      HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
      urlConn.setRequestProperty("User-agent", "Mozilla/5.0");
      urlConn.setRequestProperty(
          "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      urlConn.setRequestProperty("Accept-Language", "zh-tw,en-us;q=0.7,en;q=0.3");
      urlConn.setRequestProperty("Accept-Charse", "Big5,utf-8;q=0.7,*;q=0.7");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.connect();
      urlConn.getOutputStream().flush();
    } catch (IOException e) {
      doSuccess = false;
      System.out.println(e);
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (java.io.IOException ex) {
          System.out.print(ex);
        }
        in = null;
      }
    }
    return doSuccess;
  }
}
