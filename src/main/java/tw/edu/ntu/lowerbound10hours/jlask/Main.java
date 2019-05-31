package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.*;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.*;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Post;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.User;
import tw.edu.ntu.lowerbound10hours.jlask.session.SecureCookieSession;

class Main {
  public static void main(String[] args) throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    Jlask application = new Jlask();
    Hibernate db = new Hibernate();
    TemplateEngine templateEngine = new TemplateEngine();

    // TODO initilize database
    db.initApp(application);

    // addUrlRule
    application.addUrlRule("/", "index", new BlogView(templateEngine, db));
    application.addUrlRule("/create", "create", new CreateView(templateEngine, db));
    application.addUrlRule("/register", "register", new RegisterView(templateEngine, db));
    application.addUrlRule("/login", "login", new LoginView(templateEngine, db));
    application.addUrlRule("/update/<int:id>", "update", new UpdateView(templateEngine, db));
    application.addUrlRule("/delete/<int:id>", "delete", new DeleteView(templateEngine, db));
    application.addUrlRule("/logout", "logout", new LogOutView(templateEngine));
    Serving.runSimple(host, 8013, application);
  }
}

class RegisterView extends View {
  RegisterView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Request request = Global.request();
    Map<String, Object> context = new HashMap<>();
    SecureCookieSession session = (SecureCookieSession) Global.session();
    String errMsg = new String();
    if (request.method == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      Criteria criteria = _db.getSession().createCriteria(User.class);
      if (criteria.add(Restrictions.eq("username", username)).list().size() != 0) {
        // TODO: raise insert error if the username has existed
        errMsg = String.format("username %s has existed", username);
        System.out.println("Register fail.");
      } else {
        // test user
        User user = new User(username, password);
        _db.beginTransaction();
        _db.getSession().save(user);
        _db.getTransaction().commit();

        try {
          System.out.println("redirect\n");
          Response response = new Response("", request.environ, 302);
          response.sendRedirect("/login");
        } catch (Exception e) {
        }
      }
    }

    // TODO session.user
    context.put("user", session);
    context.put("errMsg", errMsg);
    return _templateEngine.renderTemplate("auth/register.html", context);
  }

  private TemplateEngine _templateEngine;
  private Hibernate _db;
}

class LoginView extends View {
  LoginView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Request request = Global.request();
    SecureCookieSession session = (SecureCookieSession)Global.session();
    Map<String, Object> context = new HashMap<>();
    String errMsg = new String();
    if (request.method == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      // authetication user with username and password
      Criteria criteria = _db.getSession().createCriteria(User.class);
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
        }
      }
      errMsg = "Incorrect username or password";
    }

    // TODO session.user
    context.put("user", session);
    context.put("errMsg", errMsg);
    return _templateEngine.renderTemplate("auth/login.html", context);
  }

  private TemplateEngine _templateEngine;
  private Hibernate _db;
}

class CreateView extends View {
  CreateView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> context = new HashMap<String, Object>();
    SecureCookieSession session = (SecureCookieSession)Global.session();
    Request request = Global.request();
    String title = request.getParameter("title");
    String body = request.getParameter("body");
    context.put("title", title);
    context.put("body", body);

    if (request.method == "POST") {
      // test post
      // TODO session.user
      Post post =
          new Post(
              title,
              body,
              Integer.valueOf(session.get("id")),
              session.get("username"));
      _db.beginTransaction();
      _db.getSession().save(post);
      _db.getTransaction().commit();

      try {
        Response response = new Response("", request.environ, 302);
        response.sendRedirect("/");
      } catch (Exception e) {
      }
    }

    // TODO session.user
    context.put("user", session);
    return _templateEngine.renderTemplate("blog/create.html", context);
  }

  private TemplateEngine _templateEngine;
  private Hibernate _db;
}

class BlogView extends View {
  BlogView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    ArrayList<Object> postList = new ArrayList<>();
    SecureCookieSession session = (SecureCookieSession)Global.session();

    Criteria criteria = _db.getSession().createCriteria(Post.class);
    criteria.addOrder(Order.asc("created"));

    for (int i = 0; i < criteria.list().size(); ++i) {
      Post post = (Post) criteria.list().get(i);
      postList.add(post);
    }

    Map<String, Object> context = new HashMap<String, Object>();
    context.put("postList", postList);

    // TODO session.user
    if(session.contains("username")){
        System.out.println("#############################");
        System.out.printf("%s %s\n", session.get("username"), session.get("id"));
        System.out.println("#############################");
    }
    context.put("user", session);
    return _templateEngine.renderTemplate("blog/index.html", context);
  }

  private TemplateEngine _templateEngine;
  private Hibernate _db;
}

class UpdateView extends View {
  UpdateView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> context = new HashMap<String, Object>();
    SecureCookieSession session = (SecureCookieSession)Global.session();
    Request request = Global.request();
    // test post database
    Criteria criteria = _db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("id", args.get("id")));

    Post post = (Post) criteria.list().get(0);

    if (request.method == "POST") {
      String title = request.getParameter("title");
      String body = request.getParameter("body");
      System.out.printf("%s %s\n", title, body);

      post.setTitle(title);
      post.setBody(body);
      _db.beginTransaction();
      _db.getSession().update(post);
      _db.getTransaction().commit();

      try {
        Response response = new Response("", request.environ, 302);
        response.sendRedirect("/");
      } catch (Exception e) {
      }
    }

    context.put("post", post);

    // TODO session.user
    context.put("user", session);
    return _templateEngine.renderTemplate("blog/update.html", context);
  }

  private TemplateEngine _templateEngine;
  private Hibernate _db;
}

class DeleteView extends View {
  DeleteView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Request request = Global.request();

    // test post database
    Criteria criteria = _db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("id", args.get("id")));

    // TODO DELETE FROM post WHERE id = ?", (id)
    Post _post = (Post) criteria.list().get(0);
    _db.beginTransaction();
    _db.getSession().delete(_post);
    _db.getTransaction().commit();

    try {
      Response response = new Response("", request.environ, 302);
      response.sendRedirect("/");
    } catch (Exception e) {
    }

    return "";
  }

  private TemplateEngine _templateEngine;
  private Hibernate _db;
}

class LogOutView extends View {
  LogOutView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {

    // TODO session.user
    SecureCookieSession session = (SecureCookieSession)Global.session();
    session.pop("username");
    session.pop("id");
    try {
      Response response = new Response("", Global.request().environ, 302);
      response.sendRedirect("/");
    } catch (Exception e) {
    }

    return "";
  }

  private TemplateEngine _templateEngine;
}
