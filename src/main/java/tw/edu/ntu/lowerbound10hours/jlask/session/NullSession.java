package tw.edu.ntu.lowerbound10hours.jlask.session;

public class NullSession extends SecureCookieSession {
  @Override
  public void set(String key, String value) {
    this.fail();
  }

  @Override
  public String get(String key) {
    this.fail();
    return null;
  }

  @Override
  public void pop(String key) {
    this.fail();
  }

  @Override
  public boolean contains(String key) {
    this.fail();
    return false;
  }

  private void fail() {
    throw new RuntimeException(
        "The session is unavailable because no secret "
            + "key was set.  Set the secret_key on the "
            + "application to something unique and secret.");
  }
}
