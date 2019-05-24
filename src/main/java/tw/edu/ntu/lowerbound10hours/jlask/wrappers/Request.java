package tw.edu.ntu.lowerbound10hours.jlask.wrappers;

import java.util.Map;

public class Request extends tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers.Request {
  /**
   * Extends jerkjeug.wrappers.Request.
   *
   * @param environ WSGI environ
   */
  public Request(Map<String, Object> environ) {
    super(environ);
  }
}
