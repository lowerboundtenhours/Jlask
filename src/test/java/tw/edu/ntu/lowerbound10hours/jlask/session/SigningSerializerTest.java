package tw.edu.ntu.lowerbound10hours.jlask.session;

import java.util.HashMap;
import org.testng.annotations.Test;

public class SigningSerializerTest {
  @Test
  public void testDumpsAndLoads() {
    SigningSerializer serializer = SigningSerializer.getInstance(null);
    HashMap<String, String> sessionData = new HashMap<>();
    sessionData.put("username", "Nash");
    sessionData.put("clear_password", "NashTestPassword13579");

    String signedJsonString = serializer.dumps(sessionData);
    System.out.println("signedJsonString: ");
    System.out.println(signedJsonString);
    HashMap<String, String> restoredData = serializer.loads(signedJsonString);
  }
}
