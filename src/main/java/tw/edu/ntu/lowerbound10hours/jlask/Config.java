package tw.edu.ntu.lowerbound10hours.jlask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/** Config return String, NOTE: must cast type like Integer.valueOf(config.get(key)) */
public class Config {
  private String rootPath;
  private HashMap<String, String> config = new HashMap<String, String>();
  private Gson gson;
  private Type dictType;

  /** Setup config with rootPath in current directory. */
  public Config() {
    this.rootPath = "./";
    this.gson = new Gson();
    this.dictType = new TypeToken<HashMap<String, String>>() {}.getType();
  }

  /** Setup config with rootPath. */
  public Config(String rootPath) {
    this.rootPath = rootPath;
    this.gson = new Gson();
    this.dictType = new TypeToken<HashMap<String, String>>() {}.getType();
  }

  /** Get config by key. */
  public String get(String key) {
    return this.config.get(key);
  }

  /** Put config by (key value). */
  public void put(String key, String value) {
    this.config.put(key, value);
  }

  /** Read config from json file. */
  public void fromJson(String filename) {
    Path path = Paths.get(this.rootPath, filename);
    try {
      // Read file from path
      String jsonString = new String(Files.readAllBytes(path));
      System.err.print("Reading config from: ");
      System.err.println(path.normalize().toAbsolutePath());
      // Decode JSON
      this.config = this.gson.fromJson(jsonString, this.dictType);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Reading config json failure!");
    }
  }

  /** Read config from ply file. */
  public void fromPyfile(String filename) {
    // TODO: Read file and parse to Map
  }
}
