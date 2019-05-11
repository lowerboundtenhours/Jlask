package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;

/** Model serving.py in Python Werkzeug. */
public class Serving {
  public static BaseWsgiServer makeServer(InetAddress host, int port, Application app)
      throws Exception {
    BaseWsgiServer server = new BaseWsgiServer(host, port, app);
    return server;
  }

  /** Model serving.py run_simple() in Python Werkzeug. Create a server and run it forever. */
  public static void runSimple(InetAddress hostname, int port, Application application)
      throws Exception {
    // TODO
    // use_reloader=False,
    // use_debugger=False,
    // use_evalex=True,
    // extra_files=None,
    // reloader_interval=1,
    // reloader_type="auto",
    // threaded=False,
    // processes=1,
    // request_handler=None,
    // static_files=None,
    // passthrough_errors=False,
    // ssl_context=None,
    BaseWsgiServer server = makeServer(hostname, port, application);
    server.serveForever();
  }
}
