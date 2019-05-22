package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.Map;

public class MyView extends View {
  @Override
  public String dispatchRequest(Map<String, Object> args) {
    return "foo";
  }
}
