package tw.edu.ntu.lowerbound10hours.jlask;

public abstract class View {

  /*
      class MyView(View):
          methods = ['GET']
          def dispatch_request(self, name):
              return 'Hello %s!' % name
      app.add_url_rule('/hello/<name>', view_func=MyView.as_view('myview'))
  */
  private String[] methods;
  private String name;

  public View(String name, String[] methods) {
    this.name = name;
    this.methods = methods;
  }

  public abstract String dispatchRequest();

  public String getName() {
    return this.name;
  }

  public String[] getMethods() {
    return this.methods;
  }
}
