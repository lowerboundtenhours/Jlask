package tw.edu.ntu.lowerbound10hours.jlask.templating;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.annotations.Test;

public class BaseTemplateEngineTest {
  public static TemplateEngineImpl mockImpl = mock(TemplateEngineImpl.class);

  public BaseTemplateEngineTest() {
    when(mockImpl.renderTemplate(any(), any())).thenReturn("RenderTemplate");
    when(mockImpl.renderTemplateString(any(), any())).thenReturn("RenderTemplateString");
  }

  @Test
  public void testRenderTemplate() throws Exception {
    BaseTemplateEngine engine = new BaseTemplateEngine(mockImpl);
    String rv = engine.renderTemplate("path", new HashMap<String, Object>());
    assertEquals(rv, "RenderTemplate");
  }

  @Test
  public void testRenderTemplateString() throws Exception {
    BaseTemplateEngine engine = new BaseTemplateEngine(mockImpl);
    String rv = engine.renderTemplateString("path", new HashMap<String, Object>());
    assertEquals(rv, "RenderTemplateString");
  }
}
