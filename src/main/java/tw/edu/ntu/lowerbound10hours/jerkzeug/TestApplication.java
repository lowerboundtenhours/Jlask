package tw.edu.ntu.lowerbound10hours.jerkzeug;

import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jerkzeug.serving.StartResponse;

/**
 * An example application for tests.
 */
public class TestApplication extends Application {
  /**
   * call() needs to be implemented.
   */
  public ApplicationIter<String> call(Map<String, Object> environ, StartResponse startResponse) {
    ApplicationIter<String> applicationIter = new ApplicationIter<String>();
    startResponse.startResponse(200, null, false);
    startResponse.getWrite().write("This is a test response from TestApplication");
    return applicationIter;
  }
}
