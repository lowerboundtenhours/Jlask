package tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class RequestTest {
  @Test
  public void testConstructor() {
    Request req = new Request();
    assertNotNull(req);
  }
}
