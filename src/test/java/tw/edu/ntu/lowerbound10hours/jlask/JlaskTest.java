package tw.edu.ntu.lowerbound10hours.jlask;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions.InternalServerError;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContext;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class JlaskTest {
  private static HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

  /** Setting up mock object. */
  public JlaskTest() {
    // Mock request
    when(mockedRequest.getMethod()).thenReturn("GET");
    when(mockedRequest.getCookies()).thenReturn(new Cookie[0]);
  }

  @Test
  public void testMakeResponse() throws Exception {
    @Jailbreak Jlask jlask = new Jlask();
    String rv = "foo";
    @Jailbreak Response res = jlask.make_response(rv);
    assertEquals(res.response.get(0), rv);
  }

  @Test
  public void testAddUrlRule() throws Exception {
    @Jailbreak Jlask jlask = new Jlask();
    assertEquals(jlask.viewFunctions.containsKey("index"), false);
    jlask.addUrlRule("/", "index", new MyView());
    jlask.addUrlRule("/login", "login", new MyView());
    assertEquals(jlask.viewFunctions.containsKey("login"), true);
    assertEquals(jlask.viewFunctions.containsKey("index"), true);
  }

  private Jlask buildApp() {
    Jlask app = new Jlask();
    app.addUrlRule("/", "index", new MyView());
    return app;
  }

  private Jlask buildAppAndSendReuqest() {
    @Jailbreak Jlask jlask = buildApp();
    Map<String, Object> environ = new HashMap<String, Object>();
    environ.put("baseRequest", mockedRequest);
    RequestContext ctx = new RequestContext(jlask, environ);
    ctx.push();
    return jlask;
  }

  @Test(enabled = true)
  public void testDispatchRequest() throws Exception {
    @Jailbreak Jlask jlask = this.buildAppAndSendReuqest();
    String r = jlask.dispatchRequest();
    assertEquals(r, "foo");
  }

  @Test
  public void testFinalizeRequest() throws Exception {
    @Jailbreak Jlask jlask = this.buildAppAndSendReuqest();
    @Jailbreak Response res = jlask.finalize_request("bar", false);
    assertEquals(res.response.get(0), "bar");
  }

  @Test
  public void testFullDispatachRequest() throws Exception {
    @Jailbreak Jlask jlask = this.buildAppAndSendReuqest();
    @Jailbreak Response res = jlask.full_dispatch_request();
    assertEquals(res.response.get(0), "foo");
  }

  @Test
  public void testHandleException() throws Exception {
    @Jailbreak Jlask jlask = buildApp();
    // For now handleException() will only throw InternalServerError
    assertThrows(InternalServerError.class, () -> jlask.handleException(new Exception()));
  }

  @Test
  public void testHandleUserException() throws Exception {
    @Jailbreak Jlask jlask = buildApp();
    // For now handleUserException() will only throw whatever comes in back if it is not a
    // HttpException. Otherwise, it will return HttpException
    assertThrows(Exception.class, () -> jlask.handleUserException(new Exception()));
    InternalServerError e = new InternalServerError();
    assertEquals(e, jlask.handleUserException(e));
  }

  @Test
  public void testHttpException() throws Exception {
    @Jailbreak Jlask jlask = buildApp();
    // For now handleException() will only return given HttpException
    InternalServerError e = new InternalServerError();
    assertEquals(e, jlask.handleHttpException(e));
  }

  @Test
  public void testFindErrorHandler() {
    @Jailbreak Jlask jlask = buildApp();
    // For now there is no error handler
    InternalServerError e = new InternalServerError();
    assertEquals(null, jlask.findErrorHandler(e));
  }
}
