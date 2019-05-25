package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.User;

public class BlogGlobal {

  public static Map<String, String> getLoginUser() {
    return loginUser;
  }

  public static void setLoginUser(User user) {
    Map<String, String> _user = new HashMap<String, String>();
    if (user != null) {
      _user.put("username", user.getUsername());
      _user.put("password", user.getPassword());
      _user.put("id", String.valueOf(user.getId()));
    }
    loginUser = _user;
  }

  public static int getPostId() {
    return postId++;
  }

  private static Map<String, String> loginUser = new HashMap<String, String>();
  private static int postId = 0;
}
