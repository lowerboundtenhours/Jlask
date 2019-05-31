package tw.edu.ntu.lowerbound10hours.jlask.templating;

import java.util.Map;

/** BaseTemplateEngine class. Can be extended by extending. */
public class BaseTemplateEngine {
  private TemplateEngineImpl impl;

  /**
   * Must provide an implementation.
   *
   * @param impl TemplateEngineImpl
   */
  public BaseTemplateEngine(TemplateEngineImpl impl) {
    this.impl = impl;
  }

  /**
   * Render template from file.
   *
   * @param path path to template file.
   * @param context the variables that are in the context of the template.
   * @return rendered string
   */
  public String renderTemplate(String path, Map<String, Object> context) {
    return this.impl.renderTemplate(path, context);
  }

  /**
   * Render template from string.
   *
   * @param source template in Java String type.
   * @param context the variables that are in the context of the template.
   * @return rendered string
   */
  public String renderTemplateString(String source, Map<String, Object> context) {
    return this.impl.renderTemplateString(source, context);
  }
}
