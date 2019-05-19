package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;
import tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper.Response;

public class Jlask extends Application {
  public Jlask() {
    // empty
  }

  public Response makeResponse(String rv) {
    return new Response(rv);
  }

  /**
   * Pop a RequestContext from stack and process.
   *
   * @return Response
   */
  public Response dispatchRequest() {

    RequestContext ctx = Globals.getRequestContextStack().pop();
    // TODO:
    //   1. get rule from context.
    //   2. get the corresponding ViewFunction, pass args and get returned string.
    String rv = "Hello World";
    return this.finalizeRequest(rv);
  }

  /**
   * Contruct a Jerkzeug Response Object.
   *
   * @param rv the rendered template string
   * @return Response
   */
  public Response finalizeRequest(String rv) {
    Response response = this.makeResponse(rv);
    // TODO: implement method processResponse.
    return response;
  }

  /**
   * The method server calls.
   *
   * @param environ WSGI environ
   * @param startResponse WSGI startResponse
   */
  public ApplicationIter<String> call(Map<String, Object> environ, StartResponse startResponse) {
    // TODO: ctx.pop() when error.
    RequestContext ctx = new RequestContext(environ);
    ctx.push();
    Response response = this.dispatchRequest();
    return response.call(environ, startResponse);
  }
}
