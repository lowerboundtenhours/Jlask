package tw.edu.ntu.lowerbound10hours.jlask.wrappers;
import tw.edu.ntu.lowerbound10hours.jlask.*;
import tw.edu.ntu.lowerbound10hours.jlask.session.*;

import java.util.List;
import java.util.Map;

public class Response extends tw.edu.ntu.lowerbound10hours.jerkzeug.wrappers.Response {

  /**
   * Extends jerkjeug.wrappers.Response.
   *
   * @param rv return body
   * @param environ WSGI environ
   * @param status HTTP status code
   */
  public Response(String rv, Map<String, Object> environ, int status) {
    super(rv, environ, status);
  }

  public Response(List<String> rv, Map<String, Object> environ, int status) {
    super(rv, environ, status);
  }

  public void sendRedirect(String location){
    Global.app().save_session((SecureCookieSession)Global.session(), this);    
    super.sendRedirect(location);
  }
}
