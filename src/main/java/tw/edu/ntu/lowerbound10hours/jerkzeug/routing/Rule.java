package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule implements RuleFactory {
    public String rule;
    public String subDomain = null;
    private Map map = null;
    private Pattern regex;

    public Rule(String string, Object endpoint) {
        this.rule = string;
    }
    public Rule(String string, Object endpoint, String subDomain) {
        this.rule = string;
        this.subDomain = subDomain;
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
        if (this.subDomain == null) this.subDomain = map.defaultSubDomain;
        this.compile();
    }
    public void compile() {
        /** compile the regular expression and stores it */
        ArrayList<String> regex_parts = new ArrayList<String>();
        for (ParseResult result: Utilities.parseRuleHelper(this.rule)) {
            // if converter is null, it's a static url part
            if (result.converter == null) regex_parts.add(result.variable);
            else {
                regex_parts.add(String.format(
                    "(?<%s>%s)", result.variable, result.converter.regex
                ));
            }
        }
        regex_parts.add("\\|");
        // the second part "(?<!/)(?P<__suffix__>/?)" currently not supported.
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


class ParseResult {
    public Converter converter;
    public String arguments;
    public String variable;
    public ParseResult(Converter converter, String arguments, String variable) {
        this.converter = converter;
        this.arguments = arguments;
        this.variable = variable;
    }
}
class Utilities {
    static private final Pattern rule_re = Pattern.compile(
        "(?<static>[^<]*)<(?:(?<converter>[a-zA-Z_][a-zA-Z0-9_]*)(?:\\((?<args>.*?)\\))?\\:)?(?<variable>[a-zA-Z_][a-zA-Z0-9_]*)>"
    );
    static private final String[] targets = {"static", "converter", "args", "variable"};
    static public ArrayList<ParseResult> parseRuleHelper(String rule) {
        Matcher matcher = rule_re.matcher(rule);
        ArrayList<ParseResult> results = new ArrayList<ParseResult>();
        while (matcher.find()) {
            String static_part = matcher.group("static");
            Converter converter = newConverter(matcher.group("converter"));
            String args = matcher.group("args");
            String variable = matcher.group("variable");

            if (static_part != null) results.add(new ParseResult(null, null, static_part));
            results.add(new ParseResult(converter, args, static_part));
        }
        return results;
    }
    static private Converter newConverter(String converterStr) {
        if (converterStr == null) return null;
        if (converterStr.equals("int")) {
            return new IntegerConverter();
        } else {
            // TODO: implement a converter not found exception?
            return new IntegerConverter();
        }
    }
}

class Converter {
    public String regex = "";
}
class IntegerConverter extends Converter {
    public String regex = "-?\\d+";
}
