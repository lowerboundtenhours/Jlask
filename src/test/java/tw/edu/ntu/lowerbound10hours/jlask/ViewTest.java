package tw.edu.ntu.lowerbound10hours.jlask;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import org.testng.annotations.Test;

public class ViewTest {

  class MyView extends View {
    // protected String[] methods = {"GET"};
    @Override
    public String dispatchRequest(Map<String, Object> args) {
      return "This is MyView";
    }
  }

  @Test
  public void test() throws Exception {
    assertEquals(3, 3);
  }

  @Test
  public void testBuildView() throws Exception {
    MyView view = new MyView();
    // Map<String, Object> args = new Map<String, Object>();
    assertEquals(view.dispatchRequest(null), "This is MyView");
    assertEquals(view.call(), "This is MyView");
    // assertEquals(view.getMethods().length, 1);
    // assertEquals(view.getMethods()[0], "GET");
  }
}
