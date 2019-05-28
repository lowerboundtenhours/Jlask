package tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jerkzeug.ApplicationIter;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

public class ResponseTest {
  private static HttpServletResponse mockResponse = mock(HttpServletResponse.class);
  private static StartResponse mockStartResponse = mock(StartResponse.class);

  /** Setting up mock object. */
  public ResponseTest() {
    doNothing().when(mockResponse).addCookie(any());
    doNothing().doThrow(new IOException()).when(mockResponse).sendRedirect(any());
    when(mockStartResponse.startResponse(any(), any(), any())).thenReturn(null);
    // when(mockStartResponse.startResponse(any(), any(), any())).thenReturn(null);
  }

  @Test
  public void testConstructor() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    @Jailbreak Response res = new Response("foo", environ, 200);
    assertEquals(res.response.get(0), "foo");
  }

  @Test
  public void testListConstructor() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    List<String> list = new ArrayList<>();
    list.add("foo");
    @Jailbreak Response res = new Response(list, environ, 200);
    assertEquals(res.response.get(0), "foo");
  }

  @Test
  public void testSetCookie() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    Response res = new Response("foo", environ, 200);
    res.setCookie("key", "value", "domain", "path", 0);
  }

  @Test
  public void testDeleteCookie() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    Response res = new Response("foo", environ, 200);
    res.deleteCookie("key");
  }

  @Test
  public void testRedirect() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    Response res = new Response("foo", environ, 200);
    res.sendRedirect("");
  }

  @Test
  public void testGetStatus() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    @Jailbreak Response res1 = new Response("foo", environ, 200);
    assertEquals(res1.getStatus(), 200);
    @Jailbreak Response res2 = new Response("bar", environ, 404);
    assertEquals(res2.getStatus(), 404);
  }

  @Test
  public void testGetResponseHeaders() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    @Jailbreak Response res = new Response("foo", environ, 200);
    assertEquals(res.getResponseHeaders(environ).size(), 0);
  }

  @Test
  public void testGetApplicationIter() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    @Jailbreak Response res = new Response("foo", environ, 200);
    assertEquals(res.getApplicationIter(environ).iterator().next(), "foo");
  }

  @Test
  public void testCall() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    @Jailbreak Response res = new Response("foo", environ, 200);
    ApplicationIter<String> iter = res.call(environ, mockStartResponse);
    assertEquals(iter.iterator().next(), "foo");
  }
}
