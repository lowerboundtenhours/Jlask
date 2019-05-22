package tw.edu.ntu.lowerbound10hours.jerkzeug.wrapper;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class ResponseTest {
  @Test
  public void testConstructor() {
    Response res = new Response("test");
    assertNotNull(res);
  }
}
