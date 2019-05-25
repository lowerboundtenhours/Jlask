package tw.edu.ntu.lowerbound10hours.jlask.session;

import org.testng.annotations.Test;

public class SigningSerializerTest {
  @Test
  public void testLoads() {
    SigningSerializer serializer = SigningSerializer.getInstance();
  }
}
