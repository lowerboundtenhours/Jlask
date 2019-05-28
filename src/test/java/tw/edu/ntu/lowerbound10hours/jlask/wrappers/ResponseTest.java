package tw.edu.ntu.lowerbound10hours.jlask.wrappers;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;

public class ResponseTest {
  private static HttpServletResponse mockResponse = mock(HttpServletResponse.class);

  @Test
  public void testConstructor() throws Exception {
    Map<String, Object> environ = new HashMap<>();
    environ.put("response", mockResponse);
    @Jailbreak Response res = new Response("foo", environ, 200);
    assertEquals(res.response.get(0), "foo");
  }
}
