package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import manifold.ext.api.Jailbreak;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContext;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

public class JlaskTest {
  @Test
  public void testMakeResponse() throws Exception {
    @Jailbreak Jlask jlask = new Jlask();
    String rv = "foo";
    assertEquals(jlask.make_response(rv).getBody(), rv);
  }

  class MyView extends View {
    @Override
    public String dispatchRequest(Map<String, Object> args) {
      return "foo";
    }
  }

  @Test
  public void testAddUrlRule() throws Exception {
    @Jailbreak Jlask jlask = new Jlask();
    assertEquals(jlask.viewFunctions.containsKey("index"), false);
    jlask.add_url_rule("/", "index", new MyView());
    jlask.add_url_rule("/login", "login", new MyView());
    assertEquals(jlask.viewFunctions.containsKey("login"), true);
    assertEquals(jlask.viewFunctions.containsKey("index"), true);
  }

  private Jlask buildApp() {
    Jlask app = new Jlask();
    app.add_url_rule("/", "index", new MyView());
    return app;
  }

  private Jlask buildAppAndSendReuqest() {
    @Jailbreak Jlask jlask = buildApp();
    Map<String, Object> environ = new HashMap<String, Object>();
    RequestContext ctx = new RequestContext(jlask, environ);
    ctx.push();
    return jlask;
  }

  @Test(enabled = true)
  public void testDispatchRequest() throws Exception {
    @Jailbreak Jlask jlask = this.buildAppAndSendReuqest();
    String r = jlask.dispatch_request();
    assertEquals(r, "foo");
  }

  @Test
  public void testFinalizeRequest() throws Exception {
    @Jailbreak Jlask jlask = this.buildApp();
    Response response = jlask.finalize_request("bar", false);
    assertEquals(response.getBody(), "bar");
  }

  @Test
  public void testFullDispatachRequest() throws Exception {
    @Jailbreak Jlask jlask = this.buildAppAndSendReuqest();
    Response response = jlask.full_dispatch_request();
    assertEquals(response.getBody(), "foo");
  }
}
