package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule implements RuleFactory {
  public String rule;
  public String endpoint;
  public String subdomain;
  public String host;
  private Map map = null;
  private Pattern regex;

  /** A Rule represents one URL pattern. */
  public Rule(String string, String endpoint) {
    this.rule = string;
    this.endpoint = endpoint;
  }

  /** Constructor with argument subdomain and host. */
  public Rule(String string, String endpoint, String subdomain, String host) {
    this.rule = string;
    this.endpoint = endpoint;
    this.subdomain = subdomain;
    this.host = host;
  }

  @Override
  public ArrayList<Rule> getRules() {
    ArrayList<Rule> ret = new ArrayList<Rule>();
    ret.add(this);
    return ret;
  }

  /**
   * Bind the url to a map and create a regular expression based on the information from the rule
   * itself and the defaults from the map.
   */
  public void bind(Map map, boolean rebind) {
    if (this.map != null && rebind) {
      throw new RuntimeException("Already bound.");
    }
    this.map = map;
    if (this.subdomain == null) {
      this.subdomain = map.defaultSubdomain;
    }
    this.compile();
  }

  /** compile the regular expression and stores it. */
  public void compile() {
    ArrayList<String> regexParts = new ArrayList<String>();

    if (this.map.hostMatching) {
      regexParts.add(this.host);
    } else {
      regexParts.add(this.subdomain);
    }
    regexParts.add("\\|");

    RuleParser ruleParser = new RuleParser();
    for (RuleParseResult result : ruleParser.parse(this.rule)) {
      // if converter is null, it's a static url part
      if (result.converter == null) {
        regexParts.add(result.variable);
      } else {
        regexParts.add(String.format("(?<%s>%s)", result.variable, result.converter.getRegex()));
      }
    }
    // the second part "(?<!/)(?P<__suffix__>/?)" currently not supported.
    String regex = String.format("^%s", String.join("", regexParts));
    this.regex = Pattern.compile(regex);
  }

  /**
   * Check if the rule matches a given path. Path is a string in the form ``"subdomain|/path"`` and
   * is assembled by the map. If the map is doing host matching the subdomain part will be the host
   * instead. If the rule matches a HashMap with the converted values is returned, otherwise the
   * return value is `null`.
   */
  public HashMap<String, Integer> match(String path) {
    Matcher matcher = this.regex.matcher(path);
    if (matcher.matches()) {
      HashMap<String, Integer> ret = new HashMap<String, Integer>();
      Set<String> groupNames = this.getNamedGroupCandidates(this.regex);
      for (String groupName : groupNames) {
        ret.put(groupName, Integer.parseInt(matcher.group(groupName)));
      }
      return ret;
    } else {
      return null;
    }
  }

  public Map getMap() {
    return this.map;
  }

  public String getRule() {
    return this.rule;
  }

  public Pattern getRegex() {
    return this.regex;
  }

  /** Find the names of named groups cause Java regex does not support to do so. */
  private Set<String> getNamedGroupCandidates(Pattern regexPattern) {
    String regex = regexPattern.pattern();
    Set<String> namedGroups = new TreeSet<String>();
    Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(regex);
    while (m.find()) {
      namedGroups.add(m.group(1));
    }
    return namedGroups;
  }

  class RuleParseResult {
    public RegexConverter converter;
    public String arguments;
    public String variable;

    public RuleParseResult(RegexConverter converter, String arguments, String variable) {
      this.converter = converter;
      this.arguments = arguments;
      this.variable = variable;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("RuleParseResult{");
      sb.append("converter = ").append(converter);
      sb.append(", arguments = ").append(arguments);
      sb.append(", variable = ").append(variable);
      return sb.append("}").toString();
    }
  }

  // Helper class for parsing rule to segments
  class RuleParser {
    private final Pattern ruleRegex =
        Pattern.compile(
            "(?<static>[^<]*)"
                + "<(?:(?<converter>[a-zA-Z_][a-zA-Z0-9_]*)"
                + "(?:\\((?<args>.*?)\\))?\\:)?"
                + "(?<variable>[a-zA-Z_][a-zA-Z0-9_]*)>");
    private final String[] targets = {"static", "converter", "args", "variable"};

    public ArrayList<RuleParseResult> parse(String rule) {
      Matcher matcher = ruleRegex.matcher(rule);
      ArrayList<RuleParseResult> results = new ArrayList<RuleParseResult>();
      int countFound = 0;
      while (matcher.find()) {
        countFound += 1;
        String staticPart = matcher.group("static");
        RegexConverter converter = newConverter(matcher.group("converter"));
        String args = matcher.group("args");
        String variable = matcher.group("variable");

        if (staticPart != null) {
          results.add(new RuleParseResult(null, null, staticPart));
        }
        results.add(new RuleParseResult(converter, args, variable));
      }
      if (countFound == 0) {
        // When the whole rule is static
        results.add(new RuleParseResult(null, null, rule));
      }
      return results;
    }

    private RegexConverter newConverter(String converterStr) {
      if (converterStr == null) {
        return null;
      }
      if (converterStr.equals("int")) {
        return new RegexIntegerConverter();
      } else {
        // TODO: throw a converter not found exception
        return new RegexIntegerConverter();
      }
    }
  }

  interface RegexConverter {
    public String getRegex();
  }

  class RegexIntegerConverter implements RegexConverter {
    private String regex = "-?\\d+";

    @Override
    public String getRegex() {
      return "-?\\d+";
    }
  }
  // TODO: implement more converters, e.g. float, path, uuid...
}
