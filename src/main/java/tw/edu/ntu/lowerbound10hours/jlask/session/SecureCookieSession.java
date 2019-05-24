package tw.edu.ntu.lowerbound10hours.jlask.session;

import java.util.HashMap;

public class SecureCookieSession {
  private HashMap<String, Object> dict = new HashMap<>();
  public boolean modified = false;
  public boolean accessed = false;

  public SecureCookieSession(HashMap<String, Object> initial) {
    this.dict = initial;
  }

  public void set(String key, Object value) {
    this.modified = true;
    this.accessed = true;
    this.dict.put(key, value);
  }

  public Object get(String key) {
    this.accessed = true;
    return this.dict.get(key);
  }

  public boolean getPermanent() {
    final String key = "_permanent";
    return dict.containsKey(key) ? (boolean) this.get(key) : false;
  }

  public void setPermanent(boolean value) {
    this.set("_permanent", (Object) value);
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

  public HashMap<String, Object> getDict() {
    return this.dict;
  }
}
