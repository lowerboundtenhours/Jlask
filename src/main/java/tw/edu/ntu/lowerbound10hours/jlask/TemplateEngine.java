package tw.edu.ntu.lowerbound10hours.jlask;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.FileLocator;
import com.hubspot.jinjava.loader.ResourceLocator;
import java.io.IOException;
import java.util.Map;

public class TemplateEngine {
  private Jinjava jinjava;
  private ResourceLocator loader;
  private JinjavaInterpreter interpreter;

  public TemplateEngine() {
    this.jinjava = new Jinjava();
    this.jinjava.setResourceLocator(new FileLocator());
    this.loader = this.jinjava.getResourceLocator();
    this.interpreter = this.jinjava.newInterpreter();
  }

  public String render_template(String template_name, Map<String, Object> context) {
    String template = "";
    try {
      template = this.loader.getString(template_name, Charsets.UTF_8, this.interpreter);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this.jinjava.render(template, context);
  }
}
