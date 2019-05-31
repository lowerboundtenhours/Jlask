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

public class IntegrityTest {
  private static final String name = "localhost";
  private static final int port = 8011;

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
    String baseURL = String.format("http://%s:%d", name, port);

    // GET register
    if (!doGet(String.format("%s/register", baseURL))) {
      System.err.println("Fail Get register");
      return;
    }
    // POST register with username="t" password = "1"
    if (!doPost(
        String.format("%s/register", baseURL),
        String.format("username=%s&password=%s", "t", "1"))) {
      System.err.println("Fail post register");
      return;
    }

    // GET login
    if (!doGet(String.format("%s/login", baseURL))) {
      System.err.println("Fail get login");
      return;
    }
    // POST login with username="t" password = "1"
    if (!doPost(
        String.format("%s/login", baseURL), String.format("username=%s&password=%s", "t", "1"))) {
      System.err.println("Fail post login");
      return;
    }

    // GET create
    if (!doGet(String.format("%s/create", baseURL))) {
      System.err.println("Fail get create");
    }
    // POST create
    if (!doPost(
        String.format("%s/create", baseURL), String.format("title=%s&body=%s", "Hello", "World"))) {
      System.err.println("Fail Post register");
      return;
    }

    server.getServer().stop();
  }

  public boolean doPost(String sURL, String data) {
    boolean doSuccess = false;
    java.io.BufferedWriter wr = null;
    try {
      URL url = new URL(sURL);
      HttpURLConnection URLConn = (HttpURLConnection) url.openConnection();
      URLConn.setDoOutput(true);
      URLConn.setDoInput(true);
      ((HttpURLConnection) URLConn).setRequestMethod("POST");
      URLConn.setUseCaches(false);
      URLConn.setAllowUserInteraction(true);
      HttpURLConnection.setFollowRedirects(true);
      URLConn.setInstanceFollowRedirects(true);
      URLConn.setRequestProperty("User-agent", "Mozilla/5.0");
      URLConn.setRequestProperty(
          "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      URLConn.setRequestProperty("Accept-Language", "zh-tw,en-us;q=0.7,en;q=0.3");
      URLConn.setRequestProperty("Accept-Charse", "Big5,utf-8;q=0.7,*;q=0.7");
      URLConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      URLConn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
      java.io.DataOutputStream dos = new java.io.DataOutputStream(URLConn.getOutputStream());
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

  public boolean doGet(String sURL) {
    boolean doSuccess = false;
    BufferedReader in = null;
    try {
      URL url = new URL(sURL);
      HttpURLConnection URLConn = (HttpURLConnection) url.openConnection();
      URLConn.setRequestProperty("User-agent", "Mozilla/5.0");
      URLConn.setRequestProperty(
          "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      URLConn.setRequestProperty("Accept-Language", "zh-tw,en-us;q=0.7,en;q=0.3");
      URLConn.setRequestProperty("Accept-Charse", "Big5,utf-8;q=0.7,*;q=0.7");
      URLConn.setDoInput(true);
      URLConn.setDoOutput(true);
      URLConn.connect();
      URLConn.getOutputStream().flush();
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
