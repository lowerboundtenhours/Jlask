package tw.edu.ntu.lowerbound10hours.jlask.context;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.MapAdapter;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.session.Session;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;

public class RequestContext {

  public Jlask app;
  public Request request;
  public Session session;
  public MapAdapter urlAdapter;

  /**
   * Setup Request context.
   */
  public RequestContext(Jlask app, Map<String, Object> environ) {
    /*
        if request is None:
            request = app.request_class(environ)
        self.request = request
        self.url_adapter = None
        self.session = session
        self.flashes = None
    */

    this.app = app;
    this.request = new Request(environ);
    this.urlAdapter = this.app.createUrlAdapter(this.request);
    // TODO: match request, then setup request.rule
  }

  public void match_request() {
    /*
    TODO:
        url_rule, self.request.view_args = self.url_adapter.match(return_rule=True)
    */
  }

  public void push() {
    // Binds the request context to the current context
    RequestContextStack.push(this);
  }

  public void pop() {
    RequestContextStack.pop();
  }

  public void auto_pop() {
    
  }

  // public Request getRequest() {
  //   return this.request;
  // }

  // public Session getSession() {
  //   return this.session;
  // }
}
