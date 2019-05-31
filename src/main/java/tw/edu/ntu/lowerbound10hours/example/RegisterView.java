
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

public class RegisterView extends View {
  RegisterView(TemplateEngine templateEngine, Hibernate db) {
    this.templateEngine = templateEngine;
    this.db = db;
  }

  /** override dispatchRequest. */
  public String dispatchRequest(Map<String, Object> args) {
    Request request = Global.request();
    Map<String, Object> context = new HashMap<>();
    SecureCookieSession session = (SecureCookieSession) Global.session();
    String errMsg = new String();
    if (request.method == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      Criteria criteria = db.getSession().createCriteria(User.class);
      if (criteria.add(Restrictions.eq("username", username)).list().size() != 0) {
        errMsg = String.format("username %s has existed", username);
        System.out.println("Register fail.");
      } else {
        // test user
        User user = new User(username, password);
        db.beginTransaction();
        db.getSession().save(user);
        db.getTransaction().commit();

        try {
          System.out.println("redirect\n");
          Response response = new Response("", request.environ, 302);
          response.sendRedirect("/login");
        } catch (Exception e) {
          System.out.print(e);
        }
      }
    }

    context.put("user", session);
    context.put("errMsg", errMsg);
    return templateEngine.renderTemplate("auth/register.html", context);
  }

  private TemplateEngine templateEngine;
  private Hibernate db;
}
