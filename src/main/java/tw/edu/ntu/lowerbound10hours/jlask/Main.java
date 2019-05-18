package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jlask.Config;

class MyView extends View {
  // protected String[] methods = {"GET"};
  @Override
  public String dispatchRequest(Map<String, Object> args) {
    return "foo";
  }
}

public class Main {
  /** A example for Jlask. */


  public static void main(String[] args) throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8013;
    Jlask app = new Jlask();
    app.add_url_rule("/", "index", new MyView());
    app.run(host, port);
    // Config config = new Config("./");
    // config.fromJson("config.json");
    // Serving.runSimple(host, port, app);
  }
}
