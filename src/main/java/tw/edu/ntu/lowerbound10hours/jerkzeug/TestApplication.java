package tw.edu.ntu.lowerbound10hours.jerkzeug;

import java.util.Map;
import java.util.ArrayList;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

public class TestApplication extends Application {
  public ApplicationIter<String> call(Map<String, Object> environ, StartResponse startResponse) {
    ApplicationIter<String> ApplicationIter = new ApplicationIter<String>();
    return ApplicationIter;

  }
}
