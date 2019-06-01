package tw.edu.ntu.lowerbound10hours.example;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlask.Global;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlask.View;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;

class LogOutView extends View {
  LogOutView(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  /** override dispatchRequest. */
  public String dispatchRequest(Map<String, Object> args) {

    SecureCookieSession session = (SecureCookieSession) Global.session();
    session.pop("username");
    session.pop("id");
    try {
      Response response = new Response("", Global.request().environ, 302);
      response.sendRedirect("/");
    } catch (Exception e) {
      System.out.print(e);
    }

    return "";
  }

  private TemplateEngine templateEngine;
}
