package tw.edu.ntu.lowerbound10hours.jlask;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.FileLocator;
import com.hubspot.jinjava.loader.ResourceLocator;
import java.io.IOException;
import java.util.Map;

/** Implements Flask's templating.py functionality. */
public class TemplateEngine {
  private Jinjava jinjava;
  private ResourceLocator loader;
  private JinjavaInterpreter interpreter;

  /** Basic constructor. */
  public TemplateEngine() {
    this.jinjava = new Jinjava();
    this.jinjava.setResourceLocator(new FileLocator());
    this.loader = this.jinjava.getResourceLocator();
    this.interpreter = this.jinjava.newInterpreter();
  }

  /**
   * Render template from file.
   *
   * @param templateName path to template file.
   * @param context the variables that are in the context of the template.
   * @return rendered string
   */
  public String renderTemplate(String templateName, Map<String, Object> context) {
    String template = "";
    try {
      template = this.loader.getString(templateName, Charsets.UTF_8, this.interpreter);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this.jinjava.render(template, context);
  }

  /**
   * Render template from string.
   *
   * @param source template in Java String type.
   * @param context the variables that are in the context of the template.
   * @return rendered string
   */
  public String renderTemplateString(String source, Map<String, Object> context) {
    return this.jinjava.render(source, context);
  }
}
