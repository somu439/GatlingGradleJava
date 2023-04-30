package sampleFeed;

//import gatlingdemostoreapi.Categories;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

//import static gatlingdemostoreapi.Categories.update;
import static gatlingdemostoreapi.Products.update;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class TestFeed extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://demostore.gatling.io")
            .header("Cache-Control", "no-cache")
            .contentTypeHeader("application/json")
            .acceptHeader("application/json");
    ScenarioBuilder cat = scenario("create product").exec(update);
    public static ChainBuilder initSession = exec(session -> session.set("authenticated", false));

    {
        setUp(
                cat.injectOpen(atOnceUsers(1)))
                .protocols(httpProtocol);
    }
}
