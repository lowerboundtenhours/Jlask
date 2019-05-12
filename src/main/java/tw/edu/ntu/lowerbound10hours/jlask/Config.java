package tw.edu.ntu.lowerbound10hours.jlask;

import java.util.HashMap;

public class Config extends HashMap<String, Object> {
  private String rootPath;

  public Config() {

  }

  public Config(String rootPath) {
    this.rootPath = rootPath;
  }

  public void fromJson(String filename) {
    // Read config from json file
    // filename = os.path.join(self.root_path, filename)
  }

  public void fromPyfile(String filename) {
    
  }
}
