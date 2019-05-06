package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;

public class Serving {
  public static BaseWSGIServer makeServer(InetAddress host, int port, Application app)
      throws Exception {
    BaseWSGIServer server = new BaseWSGIServer(host, port, app);
    return server;
  }

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
    BaseWSGIServer server = makeServer(hostname, port, application);
    server.serveForever();
  }
}
