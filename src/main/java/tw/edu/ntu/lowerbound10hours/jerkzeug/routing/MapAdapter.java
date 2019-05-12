package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.HashMap;

import javafx.util.Pair;

public class MapAdapter {

    private Map map;
    private String serverName, scriptName, subdomain, urlScheme, pathInfo, defaultMethod;
    public MapAdapter(Map map, String serverName, String scriptName, String subdomain, String urlScheme, String pathInfo, String defaultMethod) {
        this.map = map;
        this.serverName = serverName;
        this.scriptName = scriptName;
        this.subdomain = subdomain;
        this.urlScheme = urlScheme;
        this.pathInfo = pathInfo;
        this.defaultMethod = defaultMethod;
    }
    public Pair<Rule, HashMap<String, Integer>> match() {
        return null;
    }
    public Pair<Rule, HashMap<String, Integer>> match(String pathInfo, String method) {
        StringBuilder sb = new StringBuilder();
        if (this.map.hostMatching) sb.append(this.serverName);
        else sb.append(this.subdomain);
        sb.append("|").append(this.pathInfo);
        String path = sb.toString();

        for (Rule rule: this.map.rules) {
            HashMap<String, Integer> returnValue = rule.match(path);
            if (returnValue == null) continue;
            // TODO: raise RequestSlash or RequestRedirect under some circumstances
            return new Pair<>(rule, returnValue);
        }
        throw new RuntimeException("All rules not matched");  // TODO: use a custom NotFound excpetion.
    }
}



