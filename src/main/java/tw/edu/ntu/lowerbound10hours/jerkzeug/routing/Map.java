package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
  public ArrayList<Rule> rules = new ArrayList<>();
  private HashMap<String, ArrayList<Rule>> rulesByEndpoint = new HashMap<>();
  private boolean remap = true;
  public String defaultSubdomain = "";
  public boolean hostMatching = false;

  /** The map class stores all the URL rules and some configuration parameters. */
  public Map(List<RuleFactory> ruleFactories, String defaultSubdomain, boolean hostMatching) {
    for (RuleFactory ruleFactory : ruleFactories) {
      this.add(ruleFactory);
    }
    this.defaultSubdomain = defaultSubdomain;
    this.hostMatching = hostMatching;
  }

  public Map(List<RuleFactory> ruleFactories) {
    this(ruleFactories, "", false);
  }

  /**
   * Use passed ruleFactory to created rules and put them into this map. All rules created is bound
   * to this map (therefore compiled)
   */
  public void add(RuleFactory ruleFactory) {
    for (Rule rule : ruleFactory.getRules()) {
      rule.bind(this, false);
      this.rules.add(rule);
      if (!this.rulesByEndpoint.containsKey(rule.endpoint)) {
        this.rulesByEndpoint.put(rule.endpoint, new ArrayList<Rule>());
      }
      this.rulesByEndpoint.get(rule.endpoint).add(rule);
    }
    this.remap = true;
  }

  public MapAdapter bind(String serverName) {
    return this.bind(serverName, "/", this.defaultSubdomain, "http", "GET", "/");
  }

  public MapAdapter bind(String serverName, String scriptName) {
    return this.bind(serverName, scriptName, this.defaultSubdomain, "http", "GET", "/");
  }

  public MapAdapter bind(
      String serverName,
      String scriptName,
      String subdomain,
      String urlScheme,
      String defaultMethod,
      String pathInfo) {
    return new MapAdapter(
        this, serverName, scriptName, subdomain, urlScheme, pathInfo, defaultMethod);
  }

  // public MapAdapter bindToEnvironment(WSGIEnvironment environ, String serverName) {
  // }
}
