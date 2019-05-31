package tw.edu.ntu.lowerbound10hours.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import tw.edu.ntu.lowerbound10hours.jlask.Global;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlask.View;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Post;

public class BlogView extends View {
  BlogView(TemplateEngine templateEngine, Hibernate db) {
    this.templateEngine = templateEngine;
    this.db = db;
  }

  /** override dispatchRequest.  */
  public String dispatchRequest(Map<String, Object> args) {
    ArrayList<Object> postList = new ArrayList<>();

    Criteria criteria = db.getSession().createCriteria(Post.class);
    criteria.addOrder(Order.asc("created"));

    for (int i = 0; i < criteria.list().size(); ++i) {
      Post post = (Post) criteria.list().get(i);
      postList.add(post);
    }

    Map<String, Object> context = new HashMap<String, Object>();
    context.put("postList", postList);

    SecureCookieSession session = (SecureCookieSession) Global.session();
    context.put("user", session);
    return templateEngine.renderTemplate("blog/index.html", context);
  }

  private TemplateEngine templateEngine;
  private Hibernate db;
}
