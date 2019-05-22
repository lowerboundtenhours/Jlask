package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class JlaskTest {
  @Test
  public void testConstructor() throws Exception {
    Jlask app = new Jlask();
    assertNotNull(app);
  }
}
