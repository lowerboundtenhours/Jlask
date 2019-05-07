package tw.edu.ntu.lowerbound10hours.jerkzeug;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

public abstract class Application {
  public abstract ApplicationIter<String> call(
      Map<String, Object> environ, StartResponse startResponse);
}
