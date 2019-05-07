package tw.edu.ntu.lowerbound10hours.jerkzeug;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

public class TestApplication extends Application {
  public ApplicationIter<String> call(Map<String, Object> environ, StartResponse startResponse) {
    ApplicationIter<String> ApplicationIter = new ApplicationIter<String>();
    startResponse.startResponse(200, null, false);
    startResponse.getWrite().write("This is a test response from TestApplication");
    return ApplicationIter;
  }
}
