package tw.edu.ntu.lowerbound10hours.jlask.session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class SecureCookieSessionInterfaceTest {
  private static HttpServletRequest mockRequest = mock(HttpServletRequest.class);
  private static HttpServletResponse mockResponse = mock(HttpServletResponse.class);
  private Jlask app;
  private SecureCookieSessionInterface sessionInterface;
  private Request request;
  private Response response;

  /** Set up all fake variables for testing. */
  public SecureCookieSessionInterfaceTest() {
    when(mockRequest.getMethod()).thenReturn("GET");
    when(mockRequest.getParameter("key")).thenReturn("value");

    // set up fake app
    @Jailbreak Jlask app = new Jlask();

    // set up fake session cookie (generated by signingSerializer of sessionInterface)
    @Jailbreak
    SecureCookieSessionInterface sessionInterface =
        (SecureCookieSessionInterface) app.sessionInterface;
    SigningSerializer serializer = sessionInterface.getSigningSerializer(app);
    String signedSessionData = serializer.dumps(this.getExampleSessionData());
    when(mockRequest.getCookies())
        .thenReturn(
            new Cookie[] {
              new Cookie(sessionInterface.getSessionCookieName(app), signedSessionData)
            });
    this.app = app;
    this.sessionInterface = sessionInterface;

    // set up fake request
    Map<String, Object> environ = new HashMap<>();
    environ.put("request", mockRequest);
    Request request = new Request(environ);
    this.request = request;

    // set up fake response
    environ.put("response", mockResponse);
    @Jailbreak Response response = new Response("foo", environ, 200);
    this.response = response;
  }

  private HashMap<String, String> getExampleSessionData() {
    HashMap<String, String> data = new HashMap<>();
    data.put("username", "Nash");
    data.put("accessID", "13579");
    return data;
  }

  @Test
  public void testReadCookie() {
    // test if session data can be correctly read
    @Jailbreak SecureCookieSession session = app.open_session(request);
    assertEquals(session.dict.size(), 2);
    assertEquals(session.get("username"), "Nash");
    assertEquals(session.get("accessID"), "13579");
  }

  @Test
  public void testLoadKeyPair() {
    SigningSerializer ss = new SigningSerializer("./src/main/resources/");
    SigningSerializer ss2 = new SigningSerializer("./src/main/resources/");
  }

  @Test
  public void testSentCookie() {
    SecureCookieSession session = app.open_session(request);
    session.set("username2", "Bob");
    app.save_session(session, response);
    // NOTE: Here we don't know if the session cookie is correctly set up
    // in the response object, because we don't have a browser to interact!
    // Therefore, this test only guarentee the save_session procedure is runnable.
  }

  @Test
  public void testShouldSetCookie() {
    // TODO: only if the session is modified will we need to rewrite the session cookie
    SecureCookieSession session = app.open_session(request);
    assertEquals(sessionInterface.shouldSetCookie(app, session), false);

    // if the session is modified, the cookie need to be reset
    session.set("username2", "Bob");
    assertEquals(sessionInterface.shouldSetCookie(app, session), true);
  }

  @Test
  public void testShouldSetCookie2() {
    // TODO: only if the session is modified will we need to rewrite the session cookie
    SecureCookieSession session = app.open_session(request);
    assertEquals(sessionInterface.shouldSetCookie(app, session), false);

    // Test pop and contains
    assertEquals(session.contains("username"), true);
    session.pop("username");
    assertEquals(session.contains("username"), false);

    // if the session is modified, the cookie need to be reset
    assertEquals(sessionInterface.shouldSetCookie(app, session), true);
  }

  @Test
  public void testNullSession() {
    SecureCookieSession session = app.open_session(request);
    SecureCookieSession nullSession = this.sessionInterface.makeNullSession();
    assertEquals(sessionInterface.isNullSession(session), false);
    assertEquals(sessionInterface.isNullSession(nullSession), true);
  }

  @Test
  public void testUseNullSession() {
    SecureCookieSession nullSession = this.sessionInterface.makeNullSession();
    assertThrows(
        RuntimeException.class,
        () -> {
          nullSession.get("username");
        });
    assertThrows(
        RuntimeException.class,
        () -> {
          nullSession.set("username", "invalid usage");
        });
    assertThrows(
        RuntimeException.class,
        () -> {
          nullSession.pop("username");
        });
    assertThrows(
        RuntimeException.class,
        () -> {
          nullSession.contains("username");
        });
  }
}
