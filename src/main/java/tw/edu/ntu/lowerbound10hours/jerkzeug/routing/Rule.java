package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule implements RuleFactory {
    public String rule;
    public String endpoint;
    public String subdomain = null;
    private Map map = null;
    private Pattern regex;

    public Rule(String string, String endpoint) {
        this.rule = string;
        this.endpoint = endpoint;
    }
    public Rule(String string, String endpoint, String subdomain) {
        this.rule = string;
        this.subdomain = subdomain;
        this.endpoint = endpoint;
    }
    @Override
    public ArrayList<Rule> getRules() {
        ArrayList<Rule> ret = new ArrayList<Rule>();
        ret.add(this);
        return ret;
    }

    public void bind(Map map, boolean rebind) {
        if (this.map != null && rebind) throw new RuntimeException("Already bound.");
        this.map = map;
        if (this.subdomain == null) this.subdomain = map.defaultSubdomain;
        this.compile();
    }

    public void compile() {
        /** compile the regular expression and stores it */
        ArrayList<String> regex_parts = new ArrayList<String>();
        RuleParser ruleParser = new RuleParser();
        for (RuleParseResult result: ruleParser.parse(this.rule)) {
            // if converter is null, it's a static url part
            if (result.converter == null) regex_parts.add(result.variable);
            else {
                regex_parts.add(String.format(
                    "(?<%s>%s)", result.variable, result.converter.getRegex()
                ));
            }
        }
        // the second part "(?<!/)(?P<__suffix__>/?)" currently not supported.
        // regex_parts.add("\\|");
        String regex = String.format("^%s", String.join("", regex_parts));
        this.regex = Pattern.compile(regex);
    }

    public HashMap<String, Integer> match(String path) {
        /**Check if the rule matched a given path in the form "subdomain|/path" and
         * is assembled by the Map. If matched, return converted values in a dict.
         * Otherwise null will be returned.
         */
        
        return null;
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

class RuleParser {
    private final Pattern rule_re = Pattern.compile(
            "(?<static>[^<]*)<(?:(?<converter>[a-zA-Z_][a-zA-Z0-9_]*)(?:\\((?<args>.*?)\\))?\\:)?(?<variable>[a-zA-Z_][a-zA-Z0-9_]*)>"
            );
    private final String[] targets = {"static", "converter", "args", "variable"};
    public ArrayList<RuleParseResult> parse(String rule) {
        Matcher matcher = rule_re.matcher(rule);
        ArrayList<RuleParseResult> results = new ArrayList<RuleParseResult>();
        int count_found = 0;
        while (matcher.find()) {
            count_found += 1;
            String static_part = matcher.group("static");
            RegexConverter converter = newConverter(matcher.group("converter"));
            String args = matcher.group("args");
            String variable = matcher.group("variable");

            if (static_part != null) results.add(new RuleParseResult(null, null, static_part));
            results.add(new RuleParseResult(converter, args, variable));
        }
        if (count_found == 0) {
            // When the whole rule is static
            results.add(new RuleParseResult(null, null, rule));
        }
        System.out.println("parsed results: " + results);
        return results;
    }
    private RegexConverter newConverter(String converterStr) {
        if (converterStr == null) return null;
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
