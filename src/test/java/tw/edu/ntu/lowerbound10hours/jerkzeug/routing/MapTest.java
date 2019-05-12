package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class MapTest {
    @Test
    public void testMap() {
        ArrayList<RuleFactory> rules = new ArrayList<>();
        rules.add(new Rule("/", "index"));
        rules.add(new Rule("/downloads/", "downloads/index"));
        rules.add(new Rule("/downloads/<int:id>", "downloads/show"));
        Map map = new Map(rules);
    }
}

