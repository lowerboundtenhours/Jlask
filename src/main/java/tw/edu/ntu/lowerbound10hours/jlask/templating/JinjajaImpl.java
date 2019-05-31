package tw.edu.ntu.lowerbound10hours.jlask.templating;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jinjaja.Jinjaja;

/** Implements TemplateEngineImpl with Jinjaja. */
public class JinjajaImpl implements TemplateEngineImpl {
  private Jinjaja jinjaja;

  /** Basic constructor. */
  public JinjajaImpl() {
    this.jinjaja = new Jinjaja();
  }

  /** Currently unsupported. */
  public String renderTemplate(String templateName, Map<String, Object> context) {
    return "Not supported";
  }

  /** Returns original string. */
  public String renderTemplateString(String source, Map<String, Object> context) {
    return this.jinjaja.render(source);
  }
}
