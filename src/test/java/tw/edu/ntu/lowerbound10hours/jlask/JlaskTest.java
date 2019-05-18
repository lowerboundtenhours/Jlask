package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class JlaskTest {
  @Test
  public void testMakeResponse() throws Exception {
    Jlask jlask = new Jlask();
    String rv = "foo";
    Method m = jlask.getClass().getDeclaredMethod("make_response", rv.getClass());
    m.setAccessible(true);
    Object r = m.invoke(jlask, rv);
    assertEquals(((Response) r).getBody(), rv);
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

  @Test
  public void testDispatchRequest() throws Exception {
    Jlask jlask = buildApp();
    Method m = jlask.getClass().getDeclaredMethod("dispatch_request", null);
    m.setAccessible(true);
    Object r = m.invoke(jlask);
    assertEquals((String) r, "foo");
  }
  
}
