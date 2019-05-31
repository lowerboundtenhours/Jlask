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
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Post;

public class UpdateView extends View {
  UpdateView(TemplateEngine templateEngine, Hibernate db) {
    this.templateEngine = templateEngine;
    this.db = db;
  }

  /** ovveride dispatchRequest. */
  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> context = new HashMap<String, Object>();
    Request request = Global.request();
    // test post database
    Criteria criteria = db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("id", args.get("id")));

    Post post = (Post) criteria.list().get(0);

    if (request.method == "POST") {
      String title = request.getParameter("title");
      String body = request.getParameter("body");
      System.out.printf("%s %s\n", title, body);

      post.setTitle(title);
      post.setBody(body);
      db.beginTransaction();
      db.getSession().update(post);
      db.getTransaction().commit();

      try {
        Response response = new Response("", request.environ, 302);
        response.sendRedirect("/");
      } catch (Exception e) {
        System.out.print(e);
      }
    }

    context.put("post", post);

    SecureCookieSession session = (SecureCookieSession) Global.session();
    context.put("user", session);
    return templateEngine.renderTemplate("blog/update.html", context);
  }

  private TemplateEngine templateEngine;
  private Hibernate db;
}
