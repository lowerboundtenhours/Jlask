package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class MapAdapter {

  private RuleMap map;
  private String serverName;
  private String scriptName;
  private String subdomain;
  private String urlScheme;
  private String pathInfo;
  private String defaultMethod;

  /**
   * Returned by :meth:`RuleMap.bind` or :meth:`RuleMap.bind_to_environ` and does the URL matching
   * and building based on runtime information.
   */
  public MapAdapter(
      RuleMap map,
      String serverName,
      String scriptName,
      String subdomain,
      String urlScheme,
      String pathInfo,
      String defaultMethod) {
    this.map = map;
    this.serverName = serverName;
    this.scriptName = scriptName;
    this.subdomain = subdomain;
    this.urlScheme = urlScheme;
    this.pathInfo = pathInfo;
    this.defaultMethod = defaultMethod;
  }

  public SimpleEntry<String, HashMap<String, Object>> match(String pathInfo) {
    return this.match(pathInfo, this.defaultMethod.toUpperCase());
  }

  /**
   * Search through all rules in the bound map, return the first rule that match this pathInfo and
   * its corresponding arguments.
   */
  public SimpleEntry<String, HashMap<String, Object>> match(String pathInfo, String method) {
    StringBuilder sb = new StringBuilder();
    if (this.map.hostMatching) {
      sb.append(this.serverName);
    } else {
      sb.append(this.subdomain);
    }
    sb.append("|");
    sb.append(pathInfo);
    String path = sb.toString();

    for (Rule rule : this.map.rules) {
      HashMap<String, Object> returnValue = rule.match(path);
      if (returnValue == null) {
        continue;
      }
      // TODO: raise RequestSlash or RequestRedirect under some circumstances
      return new SimpleEntry<>(rule.endpoint, returnValue);
    }
    throw new RuntimeException("All rules not matched"); // TODO: use a custom NotFound excpetion.
  }
}
