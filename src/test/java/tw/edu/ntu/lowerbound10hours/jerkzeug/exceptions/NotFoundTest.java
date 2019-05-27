package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class NotFoundTest {
  @Test
  public void testNotFound() {
    HttpException notFound = new NotFound();
    assertEquals((int) notFound.getCode(), 404);
  }
}
