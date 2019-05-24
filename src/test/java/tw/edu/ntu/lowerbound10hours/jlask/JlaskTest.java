package tw.edu.ntu.lowerbound10hours.jlask;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions.HttpException;
import tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions.InternalServerError;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;
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
    @Jailbreak Jlask testApp = new Jlask();
    String rv = "foo";
    @Jailbreak Response res = jlask.make_response(rv);
    assertEquals(res.response.get(0), rv);
  }

  @Test
  public void testAddUrlRule() throws Exception {
    @Jailbreak Jlask testApp = new Jlask();
    assertEquals(testApp.viewFunctions.containsKey("index"), false);
    testApp.addUrlRule("/", "index", new MyView());
    testApp.addUrlRule("/login", "login", new MyView());
    assertEquals(testApp.viewFunctions.containsKey("login"), true);
    assertEquals(testApp.viewFunctions.containsKey("index"), true);
  }

  private Jlask buildApp() {
    Jlask testApp = new Jlask();
    testApp.addUrlRule("/", "index", new MyView());
    return app;
  }

  private Jlask buildAppAndSendReuqest() {
    @Jailbreak Jlask testApp = buildApp();
    Map<String, Object> environ = new HashMap<String, Object>();
    environ.put("baseRequest", mockedRequest);
    RequestContext ctx = new RequestContext(jlask, environ);
    ctx.push();
    return jlask;
  }

  @Test()
  public void testDispatchRequest() throws Exception {
    @Jailbreak Jlask testApp = this.buildAppAndSendReuqest();
    String r = testApp.dispatchRequest();
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
    @Jailbreak Jlask testApp = buildApp();
    // For now handleException() will only throw InternalServerError
    assertThrows(InternalServerError.class, () -> testApp.handleException(new Exception()));
  }

  @Test
  public void testHandleUserException() throws Exception {
    @Jailbreak Jlask testApp = buildApp();
    // For now handleUserException() will only throw whatever comes in back if it is not a
    // HttpException. Otherwise, it will return HttpException
    assertThrows(Exception.class, () -> testApp.handleUserException(new Exception()));
    InternalServerError e = new InternalServerError();
    assertEquals(e, testApp.handleUserException(e));
  }

  @Test
  public void testHttpException() throws Exception {
    @Jailbreak Jlask testApp = buildApp();
    // For now handleException() will only return given HttpException
    InternalServerError e = new InternalServerError();
    assertEquals(e, testApp.handleHttpException(e));
  }

  @Test
  public void testFindErrorHandler() {
    @Jailbreak Jlask testApp = buildApp();
    // For now there is no error handler
    InternalServerError e = new InternalServerError();
    assertEquals(null, testApp.findErrorHandler(e));
  }

  @Test
  public void testWsgiApp() throws Exception {
    @Jailbreak Jlask testApp = this.buildAppAndSendReuqest();
    
    Map<String, Object> environ = new HashMap<String, Object>();
    StartResponse startResponse = mock(StartResponse.class);
    testApp.wsgiApp(environ, startResponse);
  }

  @Test
  public void testNotFound() {
    @Jailbreak Jlask testApp = this.buildAppAndSendReuqest();
    testApp.addUrlRule("/", "index", new MyView());
    testApp.addUrlRule("/login", "login", new MyView());
    BaseWsgiServer server = Serving.makeServer(host, port, app);
  }

}
