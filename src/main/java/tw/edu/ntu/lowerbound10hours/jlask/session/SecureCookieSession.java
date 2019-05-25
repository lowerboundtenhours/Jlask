package tw.edu.ntu.lowerbound10hours.jlask.session;

import java.util.HashMap;

public class SecureCookieSession extends Session {
  protected HashMap<String, String> dict = new HashMap<>();
  public boolean modified = false;
  public boolean accessed = false;

  /** Construct session. */
  public SecureCookieSession() {
    dict = new HashMap<>();
    modified = false;
    accessed = false;
  }

  /** Construct session with initialized data. */
  public SecureCookieSession(HashMap<String, String> initial) {
    modified = false;
    accessed = false;
    this.dict = initial;
  }

  /** Set data (a key-value pair). */
  public void set(String key, String value) {
    this.modified = true;
    this.accessed = true;
    this.dict.put(key, value);
  }

  /** Get data by key. */
  public String get(String key) {
    this.accessed = true;
    return this.dict.get(key);
  }

  /** Test if an entry with a certain key is in the data. */
  public boolean contains(String key) {
    this.accessed = true;
    return this.dict.containsKey(key);
  }

  /** Delete a data entry by `key`. */
  public void pop(String key) {
    this.modified = true;
    this.accessed = true;
    this.dict.remove(key);
  }

  /**
   * Get the internal data dictionary. Note that this function should only be used by a session
   * interface, not user!
   */
  public HashMap<String, String> getDict() {
    return this.dict;
  }
}
