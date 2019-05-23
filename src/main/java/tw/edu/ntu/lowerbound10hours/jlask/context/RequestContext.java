package tw.edu.ntu.lowerbound10hours.jlask.context;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.MapAdapter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.Rule;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.session.Session;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;

public class RequestContext {

  public Jlask app;
  public Request request;
  public Session session;
  public MapAdapter urlAdapter;

  /** Setup Request context. */
  public RequestContext(Jlask app, Map<String, Object> environ) {
    /*
        if request is None:
            request = app.request_class(environ)
        self.request = request
        self.url_adapter = None
        self.session = session
        self.flashes = None
    */
    this.session = null;
    this.app = app;
    this.request = new Request(environ);
    this.urlAdapter = this.app.createUrlAdapter(this.request);
    // TODO: match request, then setup request.rule
    this.match_request();
  }

  /** Match request via urlAdapter. */
  public void match_request() {
    try {
      // TODO: url_rule, self.request.view_args = self.url_adapter.match(return_rule=True)
      SimpleEntry<Rule, HashMap<String, Object>> result = this.urlAdapter.match();
      this.request.rule = result.getKey();
      this.request.viewArgs = result.getValue();
    } catch (Exception e) {
      this.request.routingException = e;
    }
  }

  public void push() {
    // Binds the request context to the current context
    RequestContextStack.push(this);
  }

  public void pop() {
    RequestContextStack.pop();
  }

  public void auto_pop() {}

  // public Request getRequest() {
  //   return this.request;
  // }

  // public Session getSession() {
  //   return this.session;
  // }
}
