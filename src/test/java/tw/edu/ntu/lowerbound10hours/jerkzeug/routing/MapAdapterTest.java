package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import org.testng.annotations.Test;

public class MapAdapterTest {
  private MapAdapter exampleUrls() {
    ArrayList<RuleFactory> rules = new ArrayList<>();
    rules.add(new Rule("/", "index"));
    rules.add(new Rule("/downloads/", "downloads/index"));
    rules.add(new Rule("/downloads/<int:id>", "downloads/show"));

    RuleMap map = new RuleMap(rules);
    map.add(new Rule("/date/<int:year>/<int:month>/<int:date>", "date"));

    MapAdapter urls = map.bind("example.com");
    return urls;
  }

  @Test
  public void testMapAdapter() {
    MapAdapter urls = this.exampleUrls();
  }

  @Test
  public void testMatch() {
    MapAdapter urls = this.exampleUrls();
    SimpleEntry<Rule, HashMap<String, Object>> result;
    result = urls.match("/");
    assertEquals(result.getKey().endpoint, "index");
    assertEquals(result.getValue().size(), 0);

    result = urls.match("/downloads/");
    assertEquals(result.getKey().endpoint, "downloads/index");
    assertEquals(result.getValue().size(), 0);

    result = urls.match("/downloads/12");
    assertEquals(result.getKey().endpoint, "downloads/show");
    assertEquals(result.getValue().size(), 1);
    assertEquals((Integer) result.getValue().get("id"), new Integer(12));

    result = urls.match("/date/1996/8/25");
    assertEquals(result.getKey().endpoint, "date");
    assertEquals(result.getValue().size(), 3);
    assertEquals((Integer) result.getValue().get("year"), new Integer(1996));
    assertEquals((Integer) result.getValue().get("month"), new Integer(8));
    assertEquals((Integer) result.getValue().get("date"), new Integer(25));
  }

  @Test
  public void testMatch2() {
    ArrayList<RuleFactory> rules = new ArrayList<>();
    rules.add(new Rule("/update/", "update/index"));
    rules.add(new Rule("/<int:id>/update", "update/show"));

    RuleMap map = new RuleMap(rules);
    map.add(new Rule("/a/<int:b>/c/<int:d>/e/tailing", "GaLaGaLaGaLa"));
    MapAdapter urls = map.bind("example.com");

    SimpleEntry<Rule, HashMap<String, Object>> result;
    result = urls.match("/22/update");
    assertEquals(result.getKey().endpoint, "update/show");
    assertEquals(result.getValue().size(), 1);
    assertEquals((Integer) result.getValue().get("id"), new Integer(22));

    result = urls.match("/a/1/c/2222222/e/tailing");
    assertEquals(result.getKey().endpoint, "GaLaGaLaGaLa");
    assertEquals(result.getValue().size(), 2);
    assertEquals((Integer) result.getValue().get("b"), new Integer(1));
    assertEquals((Integer) result.getValue().get("d"), new Integer(2222222));
  }

  @Test
  public void testMatch3() {
    ArrayList<RuleFactory> rules = new ArrayList<>();
    rules.add(new Rule("/test/<string:name>/<int:match>/<float:point>", "getPoint"));

    RuleMap map = new RuleMap(rules);
    MapAdapter urls = map.bind("example.com");

    SimpleEntry<Rule, HashMap<String, Object>> result;
    result = urls.match("/test/Nash/55/23.731");
    assertEquals(result.getKey().endpoint, "getPoint");
    assertEquals(result.getValue().size(), 3);
    assertEquals((String) result.getValue().get("name"), "Nash");
    assertEquals((Integer) result.getValue().get("match"), new Integer(55));
    assertTrue((Float) result.getValue().get("point") - new Float(23.731) < 1e-7);
  }
}
