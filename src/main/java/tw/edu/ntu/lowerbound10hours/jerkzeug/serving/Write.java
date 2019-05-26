package tw.edu.ntu.lowerbound10hours.jerkzeug.serving;

import java.io.PrintWriter;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class Write {
  private PrintWriter writer;
  private AbstractHandler handler;

  public Write(PrintWriter writer, AbstractHandler handler) {
    this.writer = writer;
    this.handler = handler;
  }

  public void write(String data) {
    // TODO: handle header_sent
    if (data != null) {
      this.writer.printf(data);
    }
  }
}
