package tw.edu.ntu.lowerbound10hours.jlask;

import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContextStack;
import tw.edu.ntu.lowerbound10hours.jlask.session.Session;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;

public class Global {
  public static Request request() {
    return RequestContextStack.top().request;
  }

  public static Session session() {
    return RequestContextStack.top().session;
  }

  public static Jlask app() {
    return _app;
  }

  public static void setApp(Jlask app) {
    _app = app;
  }

  static Jlask _app;
  // public static G g() {
  //   return AppContextStack.top().getApp().getg();
  // }
}
