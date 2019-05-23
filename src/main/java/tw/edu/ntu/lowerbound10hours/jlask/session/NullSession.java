package tw.edu.ntu.lowerbound10hours.jlask.session;

public class NullSession extends SecureCookieSession {
  @Override
  public void set(Object key, Object value) {
    this.fail();
  }

  @Override
  public Object get(Object key) {
    this.fail();
    return null;
  }

  private void fail() {
    throw new RuntimeException(
        "The session is unavailable because no secret "
            + "key was set.  Set the secret_key on the "
            + "application to something unique and secret.");
  }
}
