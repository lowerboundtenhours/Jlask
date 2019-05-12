package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    String defaultSubDomain = "";
    private ArrayList<Rule> rules = new ArrayList<>();
    private HashMap<String, ArrayList<Rule>> rulesByEndpoint = new HashMap<>();
    private boolean remap = true;
    private String defaultSubdomain;
    public Map(List<RuleFactory> ruleFactories, String defaultSubdomain) {
        for (RuleFactory ruleFactory: ruleFactories) {
            this.add(ruleFactory);
        }
        this.defaultSubdomain = defaultSubdomain;
    }
    private void add(RuleFactory ruleFactory) {
        for (Rule rule: ruleFactory.getRules()) {
            rule.bind(this, false); 
            this.rules.add(rule);
            if (!this.rulesByEndpoint.containsKey(rule.endpoint))
                this.rulesByEndpoint.put(rule.endpoint, new ArrayList<Rule>());
            this.rulesByEndpoint.get(rule.endpoint).add(rule);
        }
        this.remap = true;
    }
    public MapAdapter bind(String serverName) {
        return this.bind(serverName, "/", this.defaultSubdomain, "http", "GET", "/");
    }
    public MapAdapter bind(String serverName, String scriptName, String subdomain, String urlScheme, String defaultMethod, String pathInfo) {
        return MapAdapter(this, serverName, scriptName, subdomain, urlScheme, pathInfo, defaultMethod);
    }
    // public MapAdapter bindToEnvironment(WSGIEnvironment environ, String serverName) {
    // }
    public void update() {

    }
}

