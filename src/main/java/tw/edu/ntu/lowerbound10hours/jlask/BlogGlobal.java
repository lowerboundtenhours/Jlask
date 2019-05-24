package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.HashMap;
import java.util.Map;

public class BlogGlobal {
  public static Map<String, Object> getDatabase() {
    return database;
  }

  public static Map<String, String> getLoginUser() {
    return loginUser;
  }

  public static void setLoginUser(Map<String, String> user) {
    loginUser = user;
  }

  public static int getPostId(){
    return postId++;
  }

  private static Map<String, Object> database = new HashMap<String, Object>();
  private static Map<String, String> loginUser = new HashMap<String, String>();
  private static int postId = 0;
}
