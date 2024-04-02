package JwtToken;


import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.http.HttpDsl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TokenGen extends Simulation {

    HttpProtocolBuilder httpConf = http
            .baseUrl("*your_base_URL*")
            .acceptHeader("application/json")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-UK,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .shareConnections();

    Map<String, String> header = Collections.singletonMap("Content-Type", "application/x-www-form-urlencoded");
    private String token = "";

    ScenarioBuilder auth = scenario("Retrieve Token")
            .exec(
                    HttpDsl.http("POST OAuth Req")
                            .post("*URL_for_Token*")
                            .formParam("resource", "*your_resource_value*")
                            .formParam("grant_type", "*your_grant_type*")
                            .formParam("client_secret", "*your_client_secret_value*")
                            .formParam("client_id", "*your_client_id_value*")
                            .headers(header)
                            .check(status().is(200))
                            .check(jmesPath("$.access_token").saveAs("access"))
            )
            .exec(session -> {
                token = session.getString("access");
                return session;
            });

    Map<String, String> headers_10 = Collections.singletonMap("Content-Type", "application/json; charset=ISO-8859-1");

    FeederBuilder<Map<String, String>> testData = iter
            .stream(
                    () -> {
                        try {
                            File directory = new File("*pathway_to_file*");
                            if (directory.isDirectory()) {
                                return Stream.of(directory.listFiles())
                                        .map(f -> Collections.singletonMap("filePath", f.getPath()))
                                        .iterator();
                            } else {
                                throw new FileNotFoundException("Samples path must point to directory");
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
            )
            .random();

    ChainBuilder myTestObjectMethod = feed(testData)
            .exec(session -> session.set("access", token))
            .exec(HttpDsl.http("")
                    .post("*the_URL_to_send_to(don't_forget_that_base_URL_above_is_automatically_stuck_to_the_front_of_this!)*")
                    .headers(headers_10)
                    .header("Authorization", "Bearer ${access}")
                    .body(RawFileBody("${filePath}")).asJson
                    .check(status().is(200))
            );

    ScenarioBuilder scn = scenario("my_actual_load_test")
            .exec(myTestObjectMethod);

    {
        setUp(
                auth.injectOpen(rampUsers(1).during(1, TimeUnit.SECONDS)),
                scn.injectPause(2, TimeUnit.SECONDS, nothingForPause())
                        .injectOpen(rampUsers(50).during(300, TimeUnit.SECONDS))
        )
                .protocols(httpConf)
                .assertions(global().responseTime().max().lt(500))
                .assertions(forAll().failedRequests().percent().lte(1))
                .assertions(global().responseTime().mean().lte(100));
    }
}