package tw.edu.ntu.lowerbound10hours.jlask.session;

import java.util.HashMap;

public class SecureCookieSession {
  private HashMap<Object, Object> dict = new HashMap<>();
  public boolean modified = false;
  public boolean accessed = false;

  public void set(Object key, Object value) {
    this.modified = true;
    this.accessed = true;
    this.dict.put(key, value);
  }

  public Object get(Object key) {
    this.accessed = true;
    return this.dict.get(key);
  }

  public boolean getPermanent() {
    final String key = "_permanent";
    return dict.containsKey(key) ? (boolean) this.get(key) : false;
  }

  public void setPermanent(boolean value) {
    this.set((Object) "_permanent", (Object) value);
  }

  public boolean contains(Object key) {
    this.accessed = true;
    return this.dict.containsKey(key);
  }

  public void pop(Object key) {
    this.modified = true;
    this.accessed = true;
    this.dict.remove(key);
  }
}
