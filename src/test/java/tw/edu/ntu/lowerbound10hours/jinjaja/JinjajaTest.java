package tw.edu.ntu.lowerbound10hours.jinjaja;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class JinjajaTest {
  @Test
  public void testRender() throws Exception {
    Jinjaja jinjaja = new Jinjaja();
    String rv = jinjaja.render("foo");
    assertEquals(rv, "foo");
  }
}
