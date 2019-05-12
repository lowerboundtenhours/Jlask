package tw.edu.ntu.lowerbound10hours.jlask.context;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;

public class RequestContext {

  private Jlask app;
  private Request request;

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
    // self.url_adapter = app.create_url_adapter(self.request)
    // if self.url_adapter is not None:
    //     self.match_request()
  }

  public void match_request() {
    /*
    TODO:
    try:
        url_rule, self.request.view_args = self.url_adapter.match(return_rule=True)
        self.request.url_rule = url_rule
    except HTTPException as e:
        self.request.routing_exception = e
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
}
