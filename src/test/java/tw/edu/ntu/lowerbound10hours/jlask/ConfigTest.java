package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class ConfigTest {
  @Test
  public void test() throws Exception {
    assertEquals(3, 3);
  }

  @Test
  public void testConfig() throws Exception {
    Config config = new Config("./");
    config.fromJson("config.json");
    assertEquals(config.get("foo"), "bar");
  }
}
