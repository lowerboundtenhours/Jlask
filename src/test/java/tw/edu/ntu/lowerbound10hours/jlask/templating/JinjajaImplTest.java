package tw.edu.ntu.lowerbound10hours.jlask.templating;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;

public class JinjajaImplTest {
  @Test
  public void testRenderTemplate() throws Exception {
    JinjajaImpl impl = new JinjajaImpl();
    Map<String, Object> context = new HashMap<>();
    context.put("name", "Tony");
    String rv = impl.renderTemplate("src/test/templates/hello.html", context);
    assertEquals(rv, "Not supported");
  }

  @Test
  public void testRenderTemplateString() throws Exception {
    JinjajaImpl impl = new JinjajaImpl();
    Map<String, Object> context = new HashMap<>();
    context.put("name", "Tony");
    String rv = impl.renderTemplateString("<p>Hello, {{ name }}!</p>", context);
    assertEquals(rv, "<p>Hello, {{ name }}!</p>");
  }
}
