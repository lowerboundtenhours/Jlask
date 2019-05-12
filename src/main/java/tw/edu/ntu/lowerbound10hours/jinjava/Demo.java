package tw.edu.ntu.lowerbound10hours.jinjava;

import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;

public class Demo {
  private TemplateEngine engine;

  public Demo() {
    this.engine = new TemplateEngine();
  }

  public void run() {
    Map<String, Object> context = new HashMap<>();
    context.put("name", "Tony");
    System.out.println(this.engine.render_template("src/main/templates/hello.html", context));
  }
}
