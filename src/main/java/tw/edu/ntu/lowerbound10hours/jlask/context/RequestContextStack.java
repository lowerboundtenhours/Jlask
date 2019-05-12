package tw.edu.ntu.lowerbound10hours.jlask.context;

import java.util.Stack;

public class RequestContextStack {
  private static RequestContextStack stack = new RequestContextStack();
  private static Stack<RequestContext> ctxStack = new Stack<RequestContext>();

  public static RequestContextStack getStack() {
    return stack;
  }

  public static boolean empty() {
    return ctxStack.empty();
  }

  public static RequestContext top() {
    return ctxStack.peek();
  }

  public static RequestContext pop() {
    return ctxStack.pop();
  }

  public static void push(RequestContext ctx) {
    ctxStack.push(ctx);
  }

  private RequestContextStack() {
    
  }
}
