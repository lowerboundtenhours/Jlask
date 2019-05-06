package tw.edu.ntu.lowerbound10hours.jerkzeug.routing;

import java.util.ArrayList;
import java.util.HashMap;

public class Rule implements RuleFactory {
    public Rule(String string, Object endpoint) {

    }
    @Override
    public ArrayList<Rule> getRules() {
        return new ArrayList<>();
    }
    public void bind(Map map) {

    }
    public void compile() {

    }
    public HashMap<Object, Object> match() {
        return null;
    }
}

