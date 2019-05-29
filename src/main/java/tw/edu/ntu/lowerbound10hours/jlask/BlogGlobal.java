package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.HashMap;
import java.util.Map;
import tw.edu.ntu.lowerbound10hours.jlaskhibernate.User;

public class BlogGlobal {

  public static User getLoginUser() {
    return loginUser;
  }

  public static void setLoginUser(User user) {
    loginUser = user;
  }

  public static int getPostId() {
    return postId++;
  }

  private static User loginUser = null;
  private static int postId = 0;
}
