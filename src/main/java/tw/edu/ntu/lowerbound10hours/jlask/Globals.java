package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.Stack;

public class Globals {
  private static Stack<RequestContext> requestContextStack;

  /**
   * Getter of Singleton RequestContext Stack.
   *
   * @return A RequestContext Stack
   */
  public static Stack<RequestContext> getRequestContextStack() {
    if (requestContextStack == null) {
      synchronized (Globals.class) {
        if (requestContextStack == null) {
          requestContextStack = new Stack<>();
        }
      }
    }

    return requestContextStack;
  }
}
