package sampleFeed;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.Session;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Products {

    public static FeederBuilder.Batchable<String> productsFeeder =
            csv("data/products.csv").circular();

    public String pName="Orange";
    public static ChainBuilder create =
            feed(productsFeeder)
//                    .exec(session -> session.set("pName","Pink"))
                    .exec(Authentication.authenticate)
                    .exec(http("Create product: #{pName}")
                            .post("/api/product")
                            .header("authorization", "Bearer #{jwt}")
                            .body(ElFileBody("gatlingdemostoreapi/demostoreapisimulation/create-product.json")));
}
