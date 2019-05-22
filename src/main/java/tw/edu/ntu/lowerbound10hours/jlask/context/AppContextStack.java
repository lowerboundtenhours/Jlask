package tw.edu.ntu.lowerbound10hours.jlask.context;

import java.util.Stack;

public class AppContextStack {
  private static AppContextStack stack = new AppContextStack();
  private static Stack<AppContext> ctxStack = new Stack<AppContext>();

  public static AppContextStack getStack() {
    return stack;
  }

  public static boolean empty() {
    return ctxStack.empty();
  }

  public static AppContext top() {
    return ctxStack.peek();
  }

  public static AppContext pop() {
    return ctxStack.pop();
  }

  public static void push(AppContext ctx) {
    ctxStack.push(ctx);
  }

  private AppContextStack() {
    // None
  }
}
