package tw.edu.ntu.lowerbound10hours.jlask.context;

import tw.edu.ntu.lowerbound10hours.jlask.Jlask;

public class AppContext {

  private Jlask app;
  // private url_adapter
  // private g

  public AppContext(Jlask app) {
    /*
    TODO:
    self.url_adapter = app.create_url_adapter(None)
    self.g = app.app_ctx_globals_class()
    */
    this.app = app;
  }

  public void push() {
    // _app_ctx_stack.push(this)
    AppContextStack.push(this);
  }

  public void pop() {
    // rv = _app_ctx_stack.pop()
    AppContextStack.pop();
  }
}
