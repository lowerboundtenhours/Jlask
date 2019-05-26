package tw.edu.ntu.lowerbound10hours.jerkzeug.exceptions;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class InternalServerErrorTest {
  @Test
  public void testInternalServerError() {
    HttpException internalServerError = new InternalServerError();
    assertEquals((int) internalServerError.getCode(), 500);
  }
}
