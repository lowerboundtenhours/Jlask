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
            regex_parts.add(String.format(
                "(?<%s>%s)", result.variable, result.converter.regex
            ));
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
}


class ParseResult {
    public Converter converter;
    public String[] arguments;
    public String variable;
}
class Utilities {
    static public ArrayList<ParseResult> parseRuleHelper(String rule) {
        return null;
    }
}

class Converter {
    public String regex = "";
}
class IntegerConverter extends Converter {
    public String regex = "-?\\d+";
}
