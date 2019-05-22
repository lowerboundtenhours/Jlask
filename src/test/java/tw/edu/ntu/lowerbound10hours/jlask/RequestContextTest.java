package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;

public class RequestContextTest {
  @Test
  public void testPushAndPop() {
    RequestContext ctx = new RequestContext();
    ctx.push();
    assertEquals(Globals.getRequestContextStack().peek(), ctx);
    RequestContext rv = ctx.pop();
    assertEquals(rv, ctx);
  }

  @Test
  public void testGet() {
    Map<String, Object> environ = new HashMap<>();
    environ.put("key", "value");
    RequestContext ctx = new RequestContext(environ);
    assertEquals(ctx.get("key"), "value");
  }
}
