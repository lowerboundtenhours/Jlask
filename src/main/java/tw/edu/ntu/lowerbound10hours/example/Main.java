package tw.edu.ntu.lowerbound10hours.example;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;
import tw.edu.ntu.lowerbound10hours.jlask.Jlask;
import tw.edu.ntu.lowerbound10hours.jlask.TemplateEngine;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.Hibernate;

class Main {
  public static void main(String[] args) throws Exception {
    Jlask application = new Jlask();
    Hibernate db = new Hibernate();
    TemplateEngine templateEngine = new TemplateEngine();

    // initilize database
    db.initApp(application);

    // addUrlRule
    application.addUrlRule("/", "index", new BlogView(templateEngine, db));
    application.addUrlRule("/create", "create", new CreateView(templateEngine, db));
    application.addUrlRule("/register", "register", new RegisterView(templateEngine, db));
    application.addUrlRule("/login", "login", new LoginView(templateEngine, db));
    application.addUrlRule("/update/<int:id>", "update", new UpdateView(templateEngine, db));
    application.addUrlRule("/delete/<int:id>", "delete", new DeleteView(templateEngine, db));
    application.addUrlRule("/logout", "logout", new LogOutView(templateEngine));
    InetAddress host = InetAddress.getByName("127.0.0.1");
    Serving.runSimple(host, 8013, application);
  }
}
