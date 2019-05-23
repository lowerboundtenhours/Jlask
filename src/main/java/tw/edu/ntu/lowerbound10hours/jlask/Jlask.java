package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.MapAdapter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.Rule;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.RuleMap;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;
import tw.edu.ntu.lowerbound10hours.jlask.context.AppContext;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContext;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContextStack;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSessionInterface;
import tw.edu.ntu.lowerbound10hours.jlask.session.Session;
import tw.edu.ntu.lowerbound10hours.jlask.session.SessionInterface;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class Jlask extends Application {
  private Config config;
  private Map<String, View> viewFunctions;
  private String staticPath;
  private SessionInterface sessionInterface;
  private RuleMap ruleMap;

  /** Init app. */
  public Jlask() {
    this.sessionInterface = new SecureCookieSessionInterface();
    this.config = new Config();
    this.viewFunctions = new HashMap<String, View>();
    this.ruleMap = new RuleMap();
  }

  /** Run api for client. */
  public void run(InetAddress host, int port) throws Exception {
    Serving.runSimple(host, port, this);
  }

  public Session open_session(Request request) {

    return new Session();
  }

  public void save_session(Session session, Response response) {
    // Save session
  }

  /** Usage: app.add_url_rule('/', 'index', index). */
  public void add_url_rule(String ruleString, String endpoint, View viewFunc) {
    /*
      URL Rule usage
      rule = self.url_rule_class(rule, methods=methods, **options)
      rule.provide_automatic_options = provide_automatic_options

      self.url_map.add(rule)
    */

    if (this.viewFunctions.containsKey(endpoint)) {
      // Rule exist
      throw new RuntimeException("View function mapping is overwriting");
    }

    Rule rule = new Rule(ruleString, endpoint);
    this.ruleMap.add(rule);
    this.viewFunctions.put(endpoint, viewFunc);
  }

  public MapAdapter createUrlAdapter(Request request) {
    return this.ruleMap.bindToEnvironment(request.environ, null, null);
  }

  private Response make_response(String rv) {

    /*
        Convert the return value from a view function to an instance of
        :attr:`response_class`

        Here we only handle rv as string
    */
    Integer status = null;
    Map<String, String> headers = null;
    Response res;
    // TODO: when rv is tuple (body, status, headers) or (body, status)
    // TODO: when rv is Response
    // TODO: when rv is bytestring

    res = new Response(rv, Global.request().environ, 200);

    return res;
  }

  private String dispatch_request() {
    /*
        Does the request dispatching. Matches the URL and returns the
        return value of the view or error handler.  This does not have to
        be a response object.
        In order to convert the return value to a
        proper response object, call :func:`make_response`
    */
    Request req = RequestContextStack.top().request;
    // TODO: req = _request_ctx_stack.top.request
    // if req.routing_exception is not None:
    //     self.raise_routing_exception(req)
    // System.err.println(req.rule.endpoint);
    // TODO:
    // rule = req.url_rule
    // return this.view_functions[rule.endpoint](**req.view_args);
    // return this.viewFunctions.get("index").call(new HashMap<String, Object>());
    return this.viewFunctions.get(req.rule.endpoint).call(req.viewArgs);
  }

  private Response full_dispatch_request() {
    /*
    Dispatches the request and on top of that performs request
    pre and postprocessing as well as HTTP exception catching and
    error handling.
    */
    String rv = null;
    try {
      // TODO: request_started.send(self) signal
      // TODO: rv = this.preprocess_request()
      if (rv == null) {
        rv = this.dispatch_request();
      }
    } catch (Exception e) {
      // TODO: handle exception
      // rv = this.handle_user_exception(e);
      System.err.print(e);
    }

    return this.finalize_request(rv, false);
  }

  private Response finalize_request(String rv, boolean fromErrorHandler) {
    /*
    Given the return value from a view function this finalizes
    the request by converting it into a response and invoking the
    postprocessing functions.  This is invoked for both normal
    request dispatching as well as error handlers.
    Because this means that it might be called as a result of a
    failure a special saf  the `from_error_handle  processing will be logged and otherwise ignored.
    */

    Response response = this.make_response(rv);
    try {
      response = this.process_response(response);
      //  request_finished.send(self, response=response) signal
    } catch (Exception e) {
      // if not fromErrorHandler:
      //     raise
      // self.logger.exception(
      //     "Request finalizing failed with an " "error while handling an error"
      // )
      System.err.print(e);
    }
    return response;
  }

  private Response process_response(Response response) {
    /*
        ctx = _request_ctx_stack.top
        bp = ctx.request.blueprint
        funcs = ctx._after_request_functions
        if bp is not None and bp in self.after_request_funcs:
            funcs = chain(funcs, reversed(self.after_request_funcs[bp]))
        if None in self.after_request_funcs:
            funcs = chain(funcs, reversed(self.after_request_funcs[None]))
        for handler in funcs:
            response = handler(response)
        if not self.session_interface.is_null_session(ctx.session):
            self.session_interface.save_session(self, ctx.session, response)
        return response
    */

    return response;
  }

  private Response handle_exception(Exception e) {
    /*
    self.log_exception((exc_type, exc_value, tb))
    handler = self._find_error_handler(InternalServerError())
    if handler is None:
        return InternalServerError()
    return self.finalize_request(handler(e), fromErrorHandler=True)
    */
    return null;
  }

  private AppContext app_context() {
    /*
    * Create AppContext'
       with app.app_context():
           init_db()
    */
    return new AppContext(this);
  }

  private RequestContext request_context(Map<String, Object> environ) {
    /*
        Create RequestContext representing a WSGI environment.
    */
    return new RequestContext(this, environ);
  }

  private ApplicationIter<String> wsgi_app(
      Map<String, Object> environ, StartResponse startResponse) {
    /*
        environ: A WSGI environment.
        start_response: A callable accepting a status code,
            a list of headers, and an optional exception context to
            start the response.

    */
    RequestContext ctx = this.request_context(environ);
    Exception error = null;

    Response response = null;
    ctx.push();
    response = this.full_dispatch_request();

    // try {
    //   try {
    //     ctx.push();
    //     response = this.full_dispatch_request();
    //   } catch (Exception e) {
    //     // TODO: handle exception
    //     error = e;
    //     System.err.print(e);
    //     // TODO: response = this.handle_exception(e);
    //   }
    //   // TODO: return response(environ, startResponse);
    // } finally {
    //   // TODO:
    //   // if self.should_ignore_error(error):
    //   //    error = None
    //   // ctx.auto_pop(error);
    // }

    // if (response != null) {
    //   startResponse.startResponse(200, null, false);
    //   startResponse.getWrite().write(response.getBody());
    // } else {
    //   startResponse.startResponse(500, null, false);
    // }
    return response.call(environ, startResponse);
  }

  public ApplicationIter<String> call(Map<String, Object> environ, StartResponse startResponse) {
    return this.wsgi_app(environ, startResponse);
  }

  public Config getConfig() {
    return this.config;
  }
}
