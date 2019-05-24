package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.MapAdapter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.Rule;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.RuleMap;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.RoutingException;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;
import tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions.InternalServerError;
import tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions.HttpException;
import tw.edu.ntu.lowerbound10hours.jlask.context.AppContext;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContext;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContextStack;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSessionInterface;
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

  public SecureCookieSession open_session(Request request) {
    return sessionInterface.openSession(this, request);
  }

  public void save_session(SecureCookieSession session, Response response) {
    sessionInterface.saveSession(this, session, response);
  }

  /** Usage: app.addUrlRule('/', 'index', index). */
  public void addUrlRule(String ruleString, String endpoint, View viewFunc) {
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

  private Response makeResponse(HttpException rv) {
    return new Response(rv);
  }

  private Response makeResponse(String rv) {

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

  private void raiseRoutingException(Request req) throws Exception {
    /* if (
            not self.debug
            or not isinstance(request.routing_exception, RequestRedirect)
            or request.method in ("GET", "HEAD", "OPTIONS")
        ):
            raise request.routing_exception

        from .debughelpers import FormDataRoutingRedirect

        raise FormDataRoutingRedirect(request) */
    throw req.routingException;
  }

  private String dispatchRequest() throws Exception {
    /*
        Does the request dispatching. Matches the URL and returns the
        return value of the view or error handler.  This does not have to
        be a response object.
        In order to convert the return value to a
        proper response object, call :func:`make_response`
    */
    Request req = RequestContextStack.top().request;
    if (req.routingException != null) {
      this.raiseRoutingException(req);
    }
    // TODO:   # if we provide automatic options for this URL and the
    //         # request came with the OPTIONS method, reply automatically
    System.err.println(req.rule.endpoint);
    return this.viewFunctions.get(req.rule.endpoint).call(req.viewArgs);
  }

  private Handler findErrorHandler(Exception e) {
    /* 
			 Return a registered error handler for an exception in this order:
       blueprint handler for a specific code, app handler for a specific code,
       blueprint handler for an exception class, app handler for an exception
       class, or ``None`` if a suitable handler is not found.
	  */

		// TODO: implement error handler map and matching
    return null;
  }

  private Object handleUserException(Exception e) throws Exception {
    /* 
			 This method is called whenever an exception occurs that
       should be handled. A special case is :class:`~werkzeug
       .exceptions.HTTPException` which is forwarded to the
       :meth:`handle_http_exception` method. This function will either
       return a response value or reraise the exception with the same
       traceback. */
    // TODO: if isinstance(e, HTTPException) and not self.trap_http_exception(e):
    //             return self.handle_http_exception(e)
    if (e instanceof HttpException) {  // TODO: not self.trap_http_exception(e)
      return this.handleHttpException((HttpException)e);
    }
    Handler handler = this.findErrorHandler(e);
    if (handler == null) {
      throw e;
    }
    return handler.call(e);
  }

  private Object handleHttpException (HttpException e) {
    /*
     * Proxy exceptions don't have error codes.  We want to always return
     * those unchanged as errors
     */
    if (e.getCode() == null) 
      return e;
   /* 
    * RoutingExceptions are used internally to trigger routing
    * actions, such as slash redirects raising RequestRedirect. They
    * are not raised or handled in user code. 
    */
    // TODO
    // if (e instanceof RoutingException) {
    //   return e;
    // }

    Handler handler = this.findErrorHandler(e);
    if (handler == null) {
      return e;
    }
    return handler.call(e);
  }

  private Response fullDispatchRequest() throws Exception {
    /*
    Dispatches the request and on top of that performs request
    pre and postprocessing as well as HTTP exception catching and
    error handling.
    */
    Object rv = null;
    try {
      // TODO: request_started.send(self) signal
      // TODO: rv = this.preprocess_request()
      if (rv == null) {
        rv = this.dispatchRequest();
      }
    } catch (Exception e) {
      rv = this.handleUserException(e);
    }

    return this.finalizeRequest(rv, false);
  }

  private Response finalizeRequest(Object rv, boolean fromErrorHandler) throws Exception {
    /*
    Given the return value from a view function this finalizes
    the request by converting it into a response and invoking the
    postprocessing functions.  This is invoked for both normal
    request dispatching as well as error handlers.
    Because this means that it might be called as a result of a
    failure a special saf  the `from_error_handle  processing will be logged and otherwise ignored.
    */

    // To handle Python duck type of rv
    Response response = null;
    if (rv instanceof HttpException) {
      response = this.makeResponse((HttpException) rv);
    } else if (rv instanceof String) {
      response = this.makeResponse((String) rv);
    }
    try {
      response = this.processResponse(response);
      //  request_finished.send(self, response=response) signal
    } catch (Exception e) {
      // if not fromErrorHandler:
      //     raise
      // self.logger.exception(
      //     "Request finalizing failed with an " "error while handling an error"
      // )
      if (fromErrorHandler)
        throw e;
      e.printStackTrace();
    }
    return response;
  }

  private Response processResponse(Response response) {
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

  private Response handleException(Exception e) throws Exception {
    /*
    got_request_exception.send(self, exception=e)
      if self.propagate_exceptions:
        ...
    self.log_exception((exc_type, exc_value, tb))
    handler = self._find_error_handler(InternalServerError())
    if handler is None:
        return InternalServerError()
    return self.finalizeRequest(handler(e), fromErrorHandler=True)
    */
		Handler handler = this.findErrorHandler(new InternalServerError());
		if (handler == null) {
      throw new InternalServerError();
    }

    return this.finalizeRequest(handler.call(e), true);
                
  }

  private AppContext appContext() {
    /*
    * Create AppContext'
       with app.app_context():
           init_db()
    */
    return new AppContext(this);
  }

  private RequestContext requestContext(Map<String, Object> environ) {
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
    RequestContext ctx = this.requestContext(environ);
    Exception error = null;

    Response response = null;
    // ctx.push();
    // response = this.full_dispatch_request();

    try {
      try {
        ctx.push();
        response = this.fullDispatchRequest();
      } catch (Exception e) {
        // e.printStackTrace();
        error = e;
        response = this.handleException(e);
      }
      System.out.println(response);
      startResponse.startResponse(response.getStatus(), null, false);
      startResponse.getWrite().write(response.getBody());
      return;
      // TODO: return response(environ, startResponse);
    } catch (InternalServerError e) {
      e.printStackTrace();
      startResponse.startResponse(500, null, false);
    } catch (Exception e) {
      // Exception from finalizeRequest
      e.printStackTrace();
      startResponse.startResponse(500, null, false);
    } finally {
      // TODO:
      // if self.shouldIgnoreError(error):
      //    error = None
      ctx.autoPop(error);
    }

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
