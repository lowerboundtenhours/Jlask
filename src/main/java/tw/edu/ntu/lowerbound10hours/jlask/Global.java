package tw.edu.ntu.lowerbound10hours.jlask;

import tw.edu.ntu.lowerbound10hours.jlask.context.AppContextStack;
import tw.edu.ntu.lowerbound10hours.jlask.context.RequestContextStack;
import tw.edu.ntu.lowerbound10hours.jlask.wrappers.Request;
import tw.edu.ntu.lowerbound10hours.jlask.session.Session;

public class Global {
  public static Request request() {
    return RequestContextStack.top().getRequest();
  }

  public static Session session() {
    return RequestContextStack.top().getSession();
  }

  public static Jlask app() {
    return AppContextStack.top().getApp();
  }

  // public static G g() {
  //   return AppContextStack.top().getApp().getg();
  // }
}
