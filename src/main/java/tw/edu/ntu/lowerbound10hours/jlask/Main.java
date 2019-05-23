package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.*;

class Main {
  public static void main(String[] args) throws Exception {
    InetAddress host;
    host = InetAddress.getByName("127.0.0.1");
    Jlask application = new Jlask();
    TemplateEngine templateEngine = new TemplateEngine();
    application.add_url_rule("/", "index", new BlogView(templateEngine));
    application.add_url_rule("/create", "create", new CreateView(templateEngine));
    application.add_url_rule("/register", "register", new RegisterView(templateEngine));
    application.add_url_rule("/login", "login", new LoginView(templateEngine));
    application.add_url_rule("/update", "update", new UpdateView(templateEngine));
    application.add_url_rule("/delete", "delete", new DeleteView(templateEngine));
    Serving.runSimple(host, 8013, application);
  }
}

class RegisterView extends View {
  RegisterView(TemplateEngine templateEngine) {
    // _request = Global.request();
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    Map<String, Object> context = new HashMap<>();
    if (request.getMethod() == "POST") {
      String userName = request.getParameter("username");
      String password = request.getParameter("password");
      System.out.printf("user: %s, password: %s\n", userName, password);
      try {
        response.sendRedirect("/login");
      } catch (Exception e) {
      }
      /* insert user account into database */

    }
    return _templateEngine.render_template("auth/register.html", context);
  }

  private TemplateEngine _templateEngine;
}

class LoginView extends View {
  LoginView(TemplateEngine templateEngine) {
    // _request = Global.request();
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    Map<String, Object> context = new HashMap<>();
    if (request.getMethod() == "POST") {
      String userName = request.getParameter("username");
      String password = request.getParameter("password");
      System.out.printf("user: %s, password: %s\n", userName, password);
      try {
        response.sendRedirect("/");
      } catch (Exception e) {
      }
      // TODO insert user account into database

    }
    return _templateEngine.render_template("auth/login.html", context);
  }

  private TemplateEngine _templateEngine;
}

class BlogView extends View {
  BlogView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> arg) {
    // TODO get post from database
    // construct fake post
    Map<String, String> testPost = new HashMap<String, String>();
    testPost.put("username", "tall15421542");
    testPost.put("created", "108.01.01");
    testPost.put("title", "my first post");
    testPost.put("body", "successful!!");
    testPost.put("id", "1");

    // body
    ArrayList<Object> postList = new ArrayList<>();
    postList.add(testPost);
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("postList", postList);
    return _templateEngine.render_template("blog/index.html", args);
  }

  private TemplateEngine _templateEngine;
}

class CreateView extends View {
  CreateView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> arg) {
    Map<String, Object> args = new HashMap<String, Object>();
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");
    String title = request.getParameter("title");
    String body = request.getParameter("body");
    args.put("title", title);
    args.put("body", body);

    if (request.getMethod() == "POST") {
      // TODO update database
      try {
        response.sendRedirect("/");
      } catch (Exception e) {
      }
    }
    return _templateEngine.render_template("blog/create.html", args);
  }

  private TemplateEngine _templateEngine;
}

class UpdateView extends View{
  UpdateView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    
    Map<String, Object> viewArgs = new HashMap<String, Object>();
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    if (request.getMethod() == "POST") {
      String title = request.getParameter("title");
      String body = request.getParameter("body");
      System.out.printf("%s %s\n", title, body);
      // TODO update database
      try {
        response.sendRedirect("/");
      } catch (Exception e) {
      }
    }
    // String id = request.getParameter("id");
    // TODO get post by id 
    
    // construct fake testPost 
    Map<String, String> testPost = new HashMap<String, String>();
    testPost.put("username", "tall15421542");
    testPost.put("created", "108.01.01");
    testPost.put("title", "my first post");
    testPost.put("body", "successful!!");
    testPost.put("id", "1");
    viewArgs.put("post", testPost);
    
    return _templateEngine.render_template("blog/update.html", viewArgs);
  }

  private TemplateEngine _templateEngine;

}

class DeleteView extends View{
  DeleteView(TemplateEngine templateEngine) {
    _templateEngine = templateEngine;
  }

  public String dispatchRequest(Map<String, Object> args) {
    
    Map<String, Object> environ = Global.request().environ;
    HttpServletRequest request = (HttpServletRequest) environ.get("request");
    HttpServletResponse response = (HttpServletResponse) environ.get("response");

    String id = request.getParameter("id");
    if(id != null){
        System.out.printf("id", id);
    }
    // TODO delete post by id 
    try{
        response.sendRedirect("/");
    }catch(Exception e){
    }
    
    // construct fake testPost 
    return "";
  }
  private TemplateEngine _templateEngine;
}
