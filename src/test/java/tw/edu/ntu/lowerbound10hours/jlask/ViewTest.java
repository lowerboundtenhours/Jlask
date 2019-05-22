package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class ViewTest {

  @Test
  public void testBuildView() throws Exception {
    MyView view = new MyView();
    assertEquals(view.dispatchRequest(null), "foo");
    assertEquals(view.call(), "foo");
  }
}
