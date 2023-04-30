package sampleFeed;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static sampleFeed.TestFeed.initSession;

public class Products {

    public static FeederBuilder.Batchable<String> productsFeeder =
            csv("data/products.csv").circular();

    public static ChainBuilder create =
            exec(initSession)
                    .exec(Authentication.authenticate)
                    .feed(productsFeeder)
                    .exec(http("Create product #{productName}")
                            .post("/api/product")
                            .headers(Headers.authorizationHeaders)
                            .body(ElFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product.json")));
}
