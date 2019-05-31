package tw.edu.ntu.lowerbound10hours.jlask.templating;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.loader.FileLocator;
import com.hubspot.jinjava.loader.ResourceLocator;
import java.io.IOException;
import java.util.Map;

/** Implements TemplateEngineImpl with Jinjava. */
public class JinjavaImpl implements TemplateEngineImpl {
  private Jinjava jinjava;
  private ResourceLocator loader;

  /** Basic constructor. */
  public JinjavaImpl() {
    this.jinjava = new Jinjava();
    this.jinjava.setResourceLocator(new FileLocator());
    this.loader = this.jinjava.getResourceLocator();
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
      template = this.loader.getString(templateName, Charsets.UTF_8, null);
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
