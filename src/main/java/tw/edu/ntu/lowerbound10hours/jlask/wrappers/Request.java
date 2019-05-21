package tw.edu.ntu.lowerbound10hours.jlask.wrappers;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.routing.Rule;

// Extend werkzeug.wrappers.Request
public class Request {
  public Map<String, Object> environ;
  public Rule rule;
  public Map<String, Object> viewArgs;

  public Request() {
    // TODO: init
  }

  public Request(Map<String, Object> environ) {
    this.environ = environ;
  }

  // public Map<String, Object> getEnv() {
  //   return this.environ;
  // }

  // public Rule getRule() {
  //   return this.rule;
  // }
}
