package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.*;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Post;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.User;

class Main {
  public static void main(String[] args) throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    Jlask application = new Jlask();
    Hibernate db = new Hibernate();
    TemplateEngine templateEngine = new TemplateEngine();

    // TODO initilize database
    db.initApp(application);

    // add_url_rule
    application.add_url_rule("/", "index", new BlogView(templateEngine, db));
    application.add_url_rule("/create", "create", new CreateView(templateEngine, db));
    application.add_url_rule("/register", "register", new RegisterView(templateEngine, db));
    application.add_url_rule("/login", "login", new LoginView(templateEngine, db));
    application.add_url_rule("/update/<int:id>", "update", new UpdateView(templateEngine, db));
    application.add_url_rule("/delete/<int:id>", "delete", new DeleteView(templateEngine, db));
    application.add_url_rule("/logout", "logout", new LogOutView(templateEngine));
    Serving.runSimple(host, 8013, application);
  }
}

class RegisterView extends View {
  RegisterView(TemplateEngine templateEngine, Hibernate db) {
    _templateEngine = templateEngine;
    _db = db;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    Map<String, Object> context = new HashMap<>();

    if (request.getMethod() == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      Criteria criteria = _db.getSession().createCriteria(User.class);
      if (criteria.add(Restrictions.eq("username", username)).list().size() != 0) {
        // TODO: raise insert error if the username has existed
        System.out.println("Register fail.");
      } else {
        // test user
        User user = new User(username, password);
        _db.beginTransaction();
        _db.getSession().save(user);
        _db.getTransaction().commit();

        try {
          response.sendRedirect("/login");
        } catch (Exception e) {
        }
      }
    }

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("auth/register.html", context);
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
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    Map<String, Object> context = new HashMap<>();
    if (request.getMethod() == "POST") {
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
        BlogGlobal.setLoginUser(user);
        try {
          response.sendRedirect("/");
        } catch (Exception e) {
        }
      }
    }

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("auth/login.html", context);
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
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    String title = request.getParameter("title");
    String body = request.getParameter("body");
    context.put("title", title);
    context.put("body", body);

    if (request.getMethod() == "POST") {
      // test post
      // TODO session.user
      Post post =
          new Post(
              title,
              body,
              Integer.parseInt(BlogGlobal.getLoginUser().get("id")),
              BlogGlobal.getLoginUser().get("username"));
      _db.beginTransaction();
      _db.getSession().save(post);
      _db.getTransaction().commit();

      try {
        response.sendRedirect("/");
      } catch (Exception e) {
      }
    }

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("blog/create.html", context);
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

    Criteria criteria = _db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("username", BlogGlobal.getLoginUser().get("username")));
    criteria.addOrder(Order.asc("created"));

    for (int i = 0; i < criteria.list().size(); ++i) {
      Post _post = (Post) criteria.list().get(i);
      Map<String, String> post = new HashMap<String, String>();
      post.put("title", _post.getTitle());
      post.put("body", _post.getBody());
      post.put("author_id", String.valueOf(_post.getAuthorId()));
      post.put("id", String.valueOf(_post.getId()));
      post.put("username", _post.getUsername());
      post.put("created", String.valueOf(_post.getCreated()));
      postList.add(post);
    }

    Map<String, Object> context = new HashMap<String, Object>();
    context.put("postList", postList);

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("blog/index.html", context);
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
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    // test post database
    Criteria criteria = _db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("id", args.get("id")));

    Post _post = (Post) criteria.list().get(0);
    Map<String, String> post = new HashMap<String, String>();
    post.put("title", _post.getTitle());
    post.put("body", _post.getBody());
    post.put("author_id", String.valueOf(_post.getAuthorId()));
    post.put("id", String.valueOf(_post.getId()));
    post.put("username", _post.getUsername());
    post.put("created", String.valueOf(_post.getCreated()));

    if (request.getMethod() == "POST") {
      String title = request.getParameter("title");
      String body = request.getParameter("body");
      System.out.printf("%s %s\n", title, body);

      _post.setTitle(title);
      _post.setBody(body);
      _db.beginTransaction();
      _db.getSession().update(_post);
      _db.getTransaction().commit();

      try {
        response.sendRedirect("/");
      } catch (Exception e) {
      }
    }

    context.put("post", post);

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("blog/update.html", context);
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
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    String id = request.getParameter("id");

    // test post database
    Criteria criteria = _db.getSession().createCriteria(Post.class);
    criteria.add(Restrictions.eq("id", args.get("id")));

    // TODO DELETE FROM post WHERE id = ?", (id)
    Post _post = (Post) criteria.list().get(0);
    _db.beginTransaction();
    _db.getSession().delete(_post);
    _db.getTransaction().commit();

    try {
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
    Map<String, Object> environ = Global.request().environ;
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    // TODO session.user
    BlogGlobal.setLoginUser(null);
    try {
      response.sendRedirect("/");
    } catch (Exception e) {
    }

    return "";
  }

  private TemplateEngine _templateEngine;
}
