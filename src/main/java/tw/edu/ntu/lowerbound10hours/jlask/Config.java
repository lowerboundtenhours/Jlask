import java.util.HashMap;

class Config extends HashMap<String, Object> {
    private String rootPath;

    public Config(String rootPath) {
        this.rootPath = rootPath;
    }

    public void fromJSON(String filename) {
        // Read config from json file
        // filename = os.path.join(self.root_path, filename) 
    }

}
