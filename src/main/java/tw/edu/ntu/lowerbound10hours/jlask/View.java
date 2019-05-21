package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.Map;

public abstract class View {

  /*
      class MyView(View):
          def dispatch_request(self, name):
              return 'Hello %s!' % name
      app.add_url_rule('/hello/<name>', view_func=MyView.as_view('myview'))
  */

  public abstract String dispatchRequest(Map<String, Object> args);

  public String call(Map<String, Object> args) {
    return this.dispatchRequest(args);
  }

  public String call() {
    return this.dispatchRequest(null);
  }
}
