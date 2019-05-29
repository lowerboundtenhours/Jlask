package tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.testng.annotations.Test;

public class RequestTest {
  private static HttpServletRequest mockRequest = mock(HttpServletRequest.class);
  private static HttpServletRequest mockNoCookieRequest = mock(HttpServletRequest.class);

  /** Setting up mock object. */
  public RequestTest() {
    when(mockRequest.getMethod()).thenReturn("GET");
    when(mockRequest.getCookies()).thenReturn(new Cookie[] {new Cookie("name", "value")});
    when(mockRequest.getParameter("key")).thenReturn("value");

    when(mockNoCookieRequest.getMethod()).thenReturn("GET");
    when(mockNoCookieRequest.getCookies()).thenReturn(null);
  }

  @Test
  public void testConstructor() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("request", mockRequest);
    Request req = new Request(environ);
    assertEquals(req.method, "GET");
    assertEquals(req.cookies.size(), 1);
    assertEquals(req.cookies.get("name").getValue(), "value");
  }

  @Test
  public void testNoCookieConstructor() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("request", mockNoCookieRequest);
    Request req = new Request(environ);
    assertEquals(req.method, "GET");
    assertEquals(req.cookies.size(), 0);
  }

  @Test
  public void testGetParameter() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("request", mockRequest);
    Request req = new Request(environ);
    assertEquals(req.getParameter("key"), "value");
  }
}
