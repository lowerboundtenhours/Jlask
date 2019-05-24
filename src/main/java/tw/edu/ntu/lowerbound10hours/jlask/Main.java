package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.*;

class Main {
  public static void main(String[] args) throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    Jlask application = new Jlask();
    TemplateEngine templateEngine = new TemplateEngine();

    // TODO initilize database
    Map<String, Object> database = BlogGlobal.getDatabase();
    database.put("user", new HashMap<String, Object>());
    database.put("post", new HashMap<String, Object>());

    // add_url_rule
    application.add_url_rule("/", "index", new BlogView(templateEngine));
    application.add_url_rule("/create", "create", new CreateView(templateEngine));
    application.add_url_rule("/register", "register", new RegisterView(templateEngine));
    application.add_url_rule("/login", "login", new LoginView(templateEngine));
    application.add_url_rule("/update/<int:id>", "update", new UpdateView(templateEngine));
    application.add_url_rule("/delete/<int:id>", "delete", new DeleteView(templateEngine));
    application.add_url_rule("/logout", "logout", new LogOutView(templateEngine));
    Serving.runSimple(host, 8013, application);
  }
}

class RegisterView extends View {
  RegisterView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    Map<String, Object> context = new HashMap<>();

    if (request.getMethod() == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      // test user table
      Map<String, Object> userDatabase = (Map<String, Object>) BlogGlobal.getDatabase().get("user");

      // TODO "SELECT id FROM user WHERE username = ?"
      // check username is unique

      // test user
      Map<String, String> user = new HashMap<>();
      user.put("username", username);
      user.put("password", password);
      user.put("id", String.valueOf(userDatabase.size()));

      // TODO "INSERT INTO user (username, password) VALUES (?, ?)"
      userDatabase.put(String.valueOf(userDatabase.size()), user);
      try {
        response.sendRedirect("/login");
      } catch (Exception e) {
      }
    }

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("auth/register.html", context);
  }

  private TemplateEngine _templateEngine;
}

class LoginView extends View {
  LoginView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    Map<String, Object> context = new HashMap<>();
    if (request.getMethod() == "POST") {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      // test user table
      Map<String, Object> userDatabase = (Map<String, Object>) BlogGlobal.getDatabase().get("user");

      // TODO "SELECT * FROM user WHERE username = ? And password = ?", (username, password)
      // authetication user with username and password

      for (Map.Entry<String, Object> entry : userDatabase.entrySet()) {
        Map<String, String> user = (Map<String, String>) entry.getValue();
        if (user.get("username").equals(username) && user.get("password").equals(password)) {
          // TODO session.user
          BlogGlobal.setLoginUser(user);
          try {
            response.sendRedirect("/");
          } catch (Exception e) {
          }
        }
      }
    }

    // TODO session.user
    context.put("user", BlogGlobal.getLoginUser());
    context.put("hasUser", !BlogGlobal.getLoginUser().isEmpty());
    return _templateEngine.render_template("auth/login.html", context);
  }

  private TemplateEngine _templateEngine;
}

class CreateView extends View {
  CreateView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
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
      // test post table
      Map<String, Object> postDatabase = (Map<String, Object>) BlogGlobal.getDatabase().get("post");

      // test post
      // TODO session.user
      Map<String, String> post = new HashMap<>();
      post.put("title", title);
      post.put("body", body);
      post.put("author_id", BlogGlobal.getLoginUser().get("id"));
      int id = BlogGlobal.getPostId();
      post.put("id", String.valueOf(id));
      post.put("username", BlogGlobal.getLoginUser().get("username"));
      post.put("created", new Date().toString());

      // TODO "INSERT INTO post (title, body, author_id)" " VALUES (?, ?, ?)",
      postDatabase.put(String.valueOf(id), post);

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
}

class BlogView extends View {
  BlogView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    ArrayList<Object> postList = new ArrayList<>();

    // TODO get post from database
    // "SELECT p.id, title, body, created, author_id, username"
    // " FROM post p JOIN user u ON p.author_id = u.id"
    // " ORDER BY created DESC"
    Map<String, Object> postDatabase = (Map<String, Object>) BlogGlobal.getDatabase().get("post");
    for (Map.Entry<String, Object> entry : postDatabase.entrySet()) {
      Map<String, String> post = (Map<String, String>) entry.getValue();
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
}

class UpdateView extends View {
  UpdateView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> context = new HashMap<String, Object>();
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    // test post database
    Map<String, Object> postDatabase = (Map<String, Object>) BlogGlobal.getDatabase().get("post");

    // TODO get post by its id
    Map<String, String> post =
        (Map<String, String>) postDatabase.get(String.valueOf(args.get("id")));

    if (request.getMethod() == "POST") {
      String title = request.getParameter("title");
      String body = request.getParameter("body");
      System.out.printf("%s %s\n", title, body);

      // TODO "UPDATE post SET title = ?, body = ? WHERE id = ?", (title, body, id)
      post.put("title", title);
      post.put("body", body);
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
}

class DeleteView extends View {
  DeleteView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    String id = request.getParameter("id");

    // test post database
    Map<String, Object> postDatabase = (Map<String, Object>) BlogGlobal.getDatabase().get("post");

    // TODO DELETE FROM post WHERE id = ?", (id)
    postDatabase.remove(String.valueOf(args.get("id")));

    try {
      response.sendRedirect("/");
    } catch (Exception e) {
    }

    return "";
  }

  private TemplateEngine _templateEngine;
}

class LogOutView extends View {
  LogOutView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    // TODO session.user
    BlogGlobal.setLoginUser(new HashMap<String, String>());
    try {
      response.sendRedirect("/");
    } catch (Exception e) {
    }

    return "";
  }

  private TemplateEngine _templateEngine;
}
