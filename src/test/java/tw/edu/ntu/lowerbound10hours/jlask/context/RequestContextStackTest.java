package tw.edu.ntu.lowerbound10hours.jlask.context;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.MyView;

public class RequestContextStackTest {

  private Jlask buildApp() {
    Jlask app = new Jlask();
    app.add_url_rule("/", "index", new MyView());
    return app;
  }

  @Test
  public void testRequestContextStack() throws Exception {

    assertEquals(RequestContextStack.empty(), false);
    Jlask jlask = buildApp();
    Map<String, Object> environ = new HashMap<String, Object>();
    RequestContext ctx = new RequestContext(jlask, environ);
    RequestContextStack.push(ctx);
    assertEquals(RequestContextStack.empty(), false);
    RequestContextStack.pop();
    RequestContextStack.top();
  }
}
