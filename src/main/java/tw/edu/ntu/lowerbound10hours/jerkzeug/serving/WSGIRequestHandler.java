package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;

public class WSGIRequestHandler extends AbstractHandler {
  public WSGIRequestHandler(Application app) {}

  public void handle(
      String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.setContentType("text/html; charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    PrintWriter out = response.getWriter();

    out.println("<h1> hi </h1>");

    baseRequest.setHandled(true);
  }
}
