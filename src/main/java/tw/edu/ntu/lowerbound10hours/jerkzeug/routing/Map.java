package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.Arrays;
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
    return this.bind(serverName, null);
  }

  public MapAdapter bind(String serverName, String scriptName) {
    return this.bind(serverName, scriptName, null, null, null, null);
  }

  /** Return a new :class:`MapAdapter` with the details specified to the call. */
  public MapAdapter bind(
      String serverName,
      String scriptName,
      String subdomain,
      String urlScheme,
      String defaultMethod,
      String pathInfo) {
    if (scriptName == null) {
      scriptName = "/";
    }
    if (subdomain == null) {
      subdomain = this.defaultSubdomain;
    }
    if (pathInfo == null) {
      pathInfo = "/";
    }

    if (urlScheme == null) {
      urlScheme = "http";
    }
    if (defaultMethod == null) {
      defaultMethod = "GET";
    }
    return new MapAdapter(
        this, serverName, scriptName, subdomain, urlScheme, pathInfo, defaultMethod);
  }

  /**
   * Like :meth:`bind` but you can pass it an WSGI environment and it will fetch the information
   * from that dictionary.
   */
  public MapAdapter bindToEnvironment(
      java.util.Map<String, Object> environ, String serverName, String subdomain) {

    String wsgiServerName = (String) environ.get("SERVER_NAME");
    wsgiServerName = wsgiServerName.toLowerCase();
    if (serverName == null) {
      serverName = wsgiServerName;
    } else {
      serverName = serverName.toLowerCase();
    }

    if (subdomain == null && !this.hostMatching) {
      String[] curServerName = wsgiServerName.split(".");
      String[] realServerName = serverName.split(".");
      // TODO: handle subdomain invalid, set subdomain = "<invalid>" like werkzeug
      String[] subdomainParts =
          Arrays.copyOfRange(curServerName, 0, curServerName.length - realServerName.length);
      subdomain = String.join(".", subdomainParts);
    }

    // TODO: check theses values are currecttly fetched from environ after its functionalities are
    // implemented
    String scriptName = (String) environ.get("SCRIPT_NAME");
    String urlScheme = (String) environ.get("wsgi.url_scheme");
    String pathInfo = (String) environ.get("PATH_INFO");
    String defaultMethod = (String) environ.get("REQUEST_METHOD");
    return this.bind(serverName, scriptName, subdomain, urlScheme, defaultMethod, pathInfo);
  }
}
