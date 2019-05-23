package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;

public class TemplateEngineTest {
  @Test
  public void testRenderTemplate() throws Exception {
    TemplateEngine engine = new TemplateEngine();
    Map<String, Object> context = new HashMap<>();
    context.put("name", "Tony");
    String rv = engine.renderTemplate("src/test/templates/hello.html", context);
    assertEquals(rv, "<p>Hello, Tony!</p>");
  }

  @Test
  public void testRenderTemplateString() throws Exception {
    TemplateEngine engine = new TemplateEngine();
    Map<String, Object> context = new HashMap<>();
    context.put("name", "Tony");
    String rv = engine.renderTemplateString("<p>Hello, {{ name }}!</p>", context);
    assertEquals(rv, "<p>Hello, Tony!</p>");
  }
}
