package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.Map;

public class RequestContext {
  private Map<String, Object> environ;

  public RequestContext(Map<String, Object> environ) {
    this.environ = environ;
  }

  public void push() {
    Globals.getRequestContextStack().push(this);
  }

  public RequestContext pop() {
    return Globals.getRequestContextStack().pop();
  }

  public Object get(String key) {
    return this.environ.get(key);
  }
}
