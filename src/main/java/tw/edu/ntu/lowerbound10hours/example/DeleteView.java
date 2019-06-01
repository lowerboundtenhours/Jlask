package tw.edu.ntu.lowerbound10hours.example;

import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import tw.edu.ntu.lowerbound10hours.jlask.Global;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlask.View;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Response;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Post;

public class DeleteView extends View {
  DeleteView(TemplateEngine templateEngine, Hibernate db) {
    this.templateEngine = templateEngine;
    this.db = db;
  }

  /** override dispatchRequesth. */
  public String dispatchRequest(Map<String, Object> args) {
    Request request = Global.request();

    // test post database
    Criteria criteria = db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("id", args.get("id")));

    Post post = (Post) criteria.list().get(0);
    db.beginTransaction();
    db.getSession().delete(post);
    db.getTransaction().commit();

    try {
      Response response = new Response("", request.environ, 302);
      response.sendRedirect("/");
    } catch (Exception e) {
      System.out.print(e);
    }

    return "";
  }

  private TemplateEngine templateEngine;
  private Hibernate db;
}
