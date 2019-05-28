package tw.edu.ntu.lowerbound10hours.jlask;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContext;

public class GlobalTest {
  private static HttpServletRequest mockRequest = mock(HttpServletRequest.class);

  private Jlask buildAppAndSendReuqest() {
    Jlask jlask = new Jlask();
    jlask.addUrlRule("/", "index", new MyView());

    Map<String, Object> environ = new HashMap<String, Object>();
    when(mockRequest.getMethod()).thenReturn("GET");
    when(mockRequest.getParameter("key")).thenReturn("value");
    environ.put("baseRequest", mockRequest);

    RequestContext ctx = new RequestContext(jlask, environ);
    ctx.push();
    return jlask;
  }

  @Test
  public void testGlobalRequest() throws Exception {
    Jlask jlask = this.buildAppAndSendReuqest();
    Global.request();
    Global.session();
  }
}
