package tw.edu.ntu.lowerbound10hours.jerkzeug;

import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class ApplicationIterTest {
  @Test
  public void testConstructor() throws Exception {
    ApplicationIter<String> iter = new ApplicationIter<>();
    assertNotNull(iter);
  }

  @Test
  public void testListConstructor() throws Exception {
    List<String> list = new ArrayList<>();
    ApplicationIter<String> iter = new ApplicationIter<>(list);
    assertNotNull(iter);
    assertNotNull(iter.iterator());
  }
}
