package tw.edu.ntu.lowerbound10hours.jlask.session;

import java.util.HashMap;

public class SecureCookieSession extends Session {
  protected HashMap<String, String> dict = new HashMap<>();
  public boolean modified = false;
  public boolean accessed = false;

  public SecureCookieSession() {
    dict = new HashMap<>();
    modified = false;
    accessed = false;
  }

  public SecureCookieSession(HashMap<String, String> initial) {
    this.dict = initial;
  }

  public void set(String key, String value) {
    this.modified = true;
    this.accessed = true;
    this.dict.put(key, value);
  }

  public String get(String key) {
    this.accessed = true;
    return this.dict.get(key);
  }

  public boolean contains(String key) {
    this.accessed = true;
    return this.dict.containsKey(key);
  }

  public void pop(String key) {
    this.modified = true;
    this.accessed = true;
    this.dict.remove(key);
  }

  public HashMap<String, String> getDict() {
    return this.dict;
  }
}
