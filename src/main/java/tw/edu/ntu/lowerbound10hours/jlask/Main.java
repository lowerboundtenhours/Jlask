package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;

public class Main {
  /** A example for Jlask. */
  public static void main(String[] args) throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8013;
    Jlask app = new Jlask();
    app.add_url_rule("/", "index", new TestView());
    app.add_url_rule("/test", "test", new TestView());
    app.run(host, port);
    // Config config = new Config("./");
    // config.fromJson("config.json");
    // Serving.runSimple(host, port, app);
  }
}
