package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.testng.annotations.Test;

public class RuleTest {
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

  private Map getMapWithRule(Rule rule) {
    ArrayList<RuleFactory> rules = new ArrayList<RuleFactory>();
    rules.add(rule);
    return new Map(rules);
  }

  @Test
  public void testCompileRegex() {
    Rule rule = new Rule("/about/<int:year>/<int:month>", "about");
    Map map = this.getMapWithRule(rule);
    Pattern answer = Pattern.compile("^\\|/about/(?<year>-?\\d+)/(?<month>-?\\d+)");
    assertEquals(rule.getRegex().pattern(), answer.pattern());
  }

  @Test
  public void testMatch() {
    Rule rule = new Rule("/about/<int:year>/<int:month>", "about");
    Map map = this.getMapWithRule(rule);

    HashMap<String, Object> matchedResult = rule.match("|/about/1996/11");

    HashMap<String, Object> answer = new HashMap<>();
    answer.put("year", new Integer(1996));
    answer.put("month", new Integer(11));
    assertEquals(matchedResult, answer);
  }
}
