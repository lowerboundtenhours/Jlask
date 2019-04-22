package tw.edu.ntu.lowerbound10hours.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Principal {

  private static final Logger LOGGER = LogManager.getLogger(Principal.class.getName());

  private Principal() {}

  public static void main(String[] args) {
    LOGGER.trace("begin of program"); // only for test
    Point p = new Point(3, 4);
    LOGGER.info(p.toString()); // a message for the user
    LOGGER.warn("a fake warning");
    LOGGER.error("a fake error");
    LOGGER.trace("end of program"); // only for test
  }
}
