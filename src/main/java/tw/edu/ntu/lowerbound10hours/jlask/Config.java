package tw.edu.ntu.lowerbound10hours.jlask;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;

public class Config extends HashMap<String, Object> {
  private String rootPath;

  public Config() {

  }

  public Config(String rootPath) {
    this.rootPath = rootPath;
  }

  public void fromJson(String filename) {
    Path path = Paths.get(this.rootPath, filename);
    System.err.println(path.normalize().toAbsolutePath());
    // TODO: Read file and parse to Map
  }

  public void fromPyfile(String filename) {
    
  }
}
