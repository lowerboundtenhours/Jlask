package tw.edu.ntu.lowerbound10hours.example;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import tw.edu.ntu.lowerbound10hours.jlask.Global;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlask.View;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.User;

public class LoginView extends View {
  LoginView(TemplateEngine templateEngine, Hibernate db) {
    this.templateEngine = templateEngine;
    this.db = db;
  }

  /** overrde dispatchRequest. */
  public String dispatchRequest(Map<String, Object> args) {
    Request request = Global.request();
    SecureCookieSession session = (SecureCookieSession) Global.session();
    Map<String, Object> context = new HashMap<>();
    String errMsg = new String();
    if (request.method == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      // authetication user with username and password
      Criteria criteria = db.getSession().createCriteria(User.class);
      criteria.add(Restrictions.eq("username", username));
      criteria.add(Restrictions.eq("password", password));
      if (criteria.list().size() == 1) {
        // autheticate successfully
        User user = (User) criteria.list().get(0);
        System.out.println(user.getUsername());
        session.set("username", username);
        session.set("id", String.valueOf(user.getId()));
        try {
          Response response = new Response("", request.environ, 302);
          response.sendRedirect("/");
        } catch (Exception e) {
          System.out.print(e);
        }
      }
      errMsg = "Incorrect username or password";
    }

    context.put("user", session);
    context.put("errMsg", errMsg);
    return templateEngine.renderTemplate("auth/login.html", context);
  }

  private TemplateEngine templateEngine;
  private Hibernate db;
}
