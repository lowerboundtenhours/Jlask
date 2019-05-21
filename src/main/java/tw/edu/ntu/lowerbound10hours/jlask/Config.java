package tw.edu.ntu.lowerbound10hours.jlask;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Config extends HashMap<String, Object> {
  private String rootPath;

  public Config() {
    // None
  }

  public Config(String rootPath) {
    this.rootPath = rootPath;
  }

  /**
   * Read config from json file.
   */
  public void fromJson(String filename) {
    Path path = Paths.get(this.rootPath, filename);
    System.err.println(path.normalize().toAbsolutePath());
    // TODO: Read file and parse to Map
  }

  /**
   * Read config from ply file.
   */
  public void fromPyfile(String filename) {
    // TODO: Read file and parse to Map
  }
}
