package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;

public class JlaskTest {
  @Test
  public void testMakeResponse() throws Exception {
    @Jailbreak Jlask jlask = new Jlask();
    String rv = "foo";
    // Method m = jlask.getClass().getDeclaredMethod("make_response", rv.getClass());
    // m.setAccessible(true);
    // Object r = m.invoke(jlask, rv);
    // assertEquals(((Response) r).getBody(), rv);
    assertEquals(jlask.make_response(rv).getBody(), rv);
  }

  class MyView extends View {
    // protected String[] methods = {"GET"};
    @Override
    public String dispatchRequest(Map<String, Object> args) {
      return "foo";
    }
  }

  private Jlask buildApp() {
    Jlask app = new Jlask();
    app.add_url_rule("/", "index", new MyView());
    return app;
  }

  @Test(enabled = false)
  public void testDispatchRequest() throws Exception {
    @Jailbreak Jlask jlask = buildApp();
    String r = jlask.dispatch_request();
    // Method m = jlask.getClass().getDeclaredMethod("dispatch_request", null);
    // m.setAccessible(true);
    // Object r = m.invoke(jlask);
    // assertEquals((String) r, "foo");
    assertEquals(r, "foo");
  }
}
