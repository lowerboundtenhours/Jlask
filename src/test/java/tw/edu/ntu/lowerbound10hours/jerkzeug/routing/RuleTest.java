package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class RuleTest{
    @Test
    public void testGetRules() {
        Rule rule = new Rule("/index", "index");
        ArrayList<Rule> ret = rule.getRules();
        assertSame(ret.get(0), rule);
    }

    @Test
    public void testBind() {
        Rule rule = new Rule("/index", "index");
        Map map = new Map(new ArrayList<RuleFactory>());
        boolean rebind = false;

        rule.bind(map, rebind);
        assertSame(rule.getMap(), map);
        assertNotNull(rule.getRegex());
    }

    @Test
    public void testCompileRegex() {
        Rule rule = new Rule("/about/<int:year>/<int:month>", "about");
        rule.compile();
        Pattern ans = Pattern.compile("^/about/(?<year>-?\\d+)/(?<month>-?\\d+)");
        assertEquals(rule.getRegex().pattern(), ans.pattern());
    }
    // public void compile() {
    //     /** compile the regular expression and stores it */
    //     ArrayList<String> regex_parts = new ArrayList<String>();
    //     for (ParseResult result: Utilities.parseRuleHelper(this.rule)) {
    //         regex_parts.add(String.format(
    //             "(?<%s>%s)", result.variable, result.converter.regex
    //         ));
    //     }
    //     regex_parts.add("\\|");
    //     // the second part "(?<!/)(?P<__suffix__>/?)" currently not supported.
    //     String regex = String.format("^%s", String.join("", regex_parts));
    //     this.regex = Pattern.compile(regex);
    // }
    // public HashMap<String, Integer> match(String path) {
    //     /**Check if the rule matched a given path in the form "subdomain|/path" and
    //      * is assembled by the Map. If matched, return converted values in a dict.
    //      * Otherwise null will be returned.
    //      */
    //     return null;
    // }
}
