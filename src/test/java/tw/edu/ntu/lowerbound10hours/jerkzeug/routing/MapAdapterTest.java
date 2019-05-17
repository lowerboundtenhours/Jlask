package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import org.testng.annotations.Test;

public class MapAdapterTest {
  private MapAdapter exampleUrls() {
    ArrayList<RuleFactory> rules = new ArrayList<>();
    rules.add(new Rule("/", "index"));
    rules.add(new Rule("/downloads/", "downloads/index"));
    rules.add(new Rule("/downloads/<int:id>", "downloads/show"));

    Map map = new Map(rules);
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
    Pair<String, HashMap<String, Object>> result;
    result = urls.match("/");
    assertEquals(result.getKey(), "index");
    assertEquals(result.getValue().size(), 0);

    result = urls.match("/downloads/");
    assertEquals(result.getKey(), "downloads/index");
    assertEquals(result.getValue().size(), 0);

    result = urls.match("/downloads/12");
    assertEquals(result.getKey(), "downloads/show");
    assertEquals(result.getValue().size(), 1);
    assertEquals((Integer) result.getValue().get("id"), new Integer(12));

    result = urls.match("/date/1996/8/25");
    assertEquals(result.getKey(), "date");
    assertEquals(result.getValue().size(), 3);
    assertEquals((Integer) result.getValue().get("year"), new Integer(1996));
    assertEquals((Integer) result.getValue().get("month"), new Integer(8));
    assertEquals((Integer) result.getValue().get("date"), new Integer(25));
  }
}
