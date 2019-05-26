package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

public class HttpExceptionTest {

  @Test
  public void testHttpException() {
    HttpException httpException = new HttpException();
    assertNull(httpException.getCode());
    assertNull(httpException.getDescription());
  }

  @Test
  public void testHttpExceptionWithDescription() {
    HttpException httpException = new HttpException("foo");
    assertNull(httpException.getCode());
    assertEquals(httpException.getDescription(), "foo");
  }
}
