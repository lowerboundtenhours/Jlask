package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContext;

public class GlobalTest {

  private Jlask buildAppAndSendReuqest() {
    Jlask jlask = new Jlask();
    jlask.addUrlRule("/", "index", new MyView());
    Map<String, Object> environ = new HashMap<String, Object>();
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
