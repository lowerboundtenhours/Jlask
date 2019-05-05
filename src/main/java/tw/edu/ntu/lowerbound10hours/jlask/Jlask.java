import java.util.Map;


public class Jlask {
    private Config config;
    private Map<String, View> viewFunctions;
    private String static_path;
    private SessionInterface sessionInterface;

    public Jlask() {
        sessionInterface = new SecureCookieSessionInterface();
    }

    public void run(String host, int port) {

    }

    public Session open_session(Request request) {

        return new Session();
    }

    public void save_session(Session session, Response response) {

    }   

    public void add_url_rule(String rule, String endpoint, View viewFunc) {
        /*
            Usage:
                app.add_url_rule('/', 'index', index)
         */

        /*
            URL Rule usage
            rule = self.url_rule_class(rule, methods=methods, **options)
            rule.provide_automatic_options = provide_automatic_options

            self.url_map.add(rule)
        */

        if (this.viewFunctions.containsKey(endpoint)) {
            // Rule exist
            throw new RuntimeException("View function mapping is overwriting");
        }
        this.viewFunctions.put(endpoint, viewFunc);


    }
    
    public Response __call__(String environ, Response startResponse) {
        return new Response();
    }
}
