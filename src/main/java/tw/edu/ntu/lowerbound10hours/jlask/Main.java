package tw.edu.ntu.lowerbound10hours.jlask;

import java.net.InetAddress;
import tw.edu.ntu.lowerbound10hours.jerkzeug.Application;
import tw.edu.ntu.lowerbound10hours.jerkzeug.TestApplication;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.Serving;

public class Main {
  /**
   * A example for Jlask.
   */
  public static void main(String[] args) throws Exception {
    InetAddress host = InetAddress.getByName("127.0.0.1");
    int port = 8013;
    Application testApp = new TestApplication();
    Serving.runSimple(host, port, testApp);
  }
}
