package tw.edu.ntu.lowerbound10hours.example;

import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlask.Global;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlask.View;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Post;

public class CreateView extends View {
  CreateView(TemplateEngine templateEngine, Hibernate db) {
    this.templateEngine = templateEngine;
    this.db = db;
  }

  /** override dispatchRequest. */
  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> context = new HashMap<String, Object>();
    SecureCookieSession session = (SecureCookieSession) Global.session();
    Request request = Global.request();
    String title = request.getParameter("title");
    String body = request.getParameter("body");
    context.put("title", title);
    context.put("body", body);

    if (request.method == "POST") {
      Post post =
          new Post(title, body, Integer.valueOf(session.get("id")), session.get("username"));
      db.beginTransaction();
      db.getSession().save(post);
      db.getTransaction().commit();

      try {
        Response response = new Response("", request.environ, 302);
        response.sendRedirect("/");
      } catch (Exception e) {
        System.out.print(e);
      }
    }

    context.put("user", session);
    return templateEngine.renderTemplate("blog/create.html", context);
  }

  private TemplateEngine templateEngine;
  private Hibernate db;
}
