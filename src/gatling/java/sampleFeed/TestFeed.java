package sampleFeed;

import gatlingdemostoreapi.Categories;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static sampleFeed.Authentication.authenticate;
import static sampleFeed.Products.create;

public class TestFeed extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://demostore.gatling.io")
            .header("Cache-Control", "no-cache")
            .contentTypeHeader("application/json");
//            .acceptHeader("application/json");

    static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    public static ScenarioBuilder testFeed = scenario("feed test")
            .exec(session -> session.set("pName",timeStamp))
//            .exec(authenticate)
            .exec(create);
    {
        setUp(
                testFeed.injectOpen(atOnceUsers(1)))
                .protocols(httpProtocol);
    }
}
