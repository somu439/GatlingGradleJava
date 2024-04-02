package JwtToken;

import io.gatling.core.Predef.*;
import io.gatling.core.scenario.Simulation;
import io.gatling.http.Predef.*;
import java.util.Base64;

import static io.gatling.core.Predef.exec;
import static io.gatling.core.Predef.jmesPath;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class JwtTokenGenerator extends Simulation {

    private static final String BASE_URL = "http://example.com";
    private static final String TOKEN_URL = BASE_URL + "/generateToken";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String generateJwtToken() {
        String credentials = USERNAME + ":" + PASSWORD;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        return exec(http("Generate JWT Token")
                .post(TOKEN_URL)
                .header("Authorization", "Basic " + base64Credentials)
                .check(status().is(200))
                .check(jmesPath("$.jwt").saveAs("jwt"))) // Save JWT token to session
                .exec(session -> session.get("jwt").as(String.class)) // Extract JWT token from session
                .toString();
    }

    public static void main(String[] args) {
        String jwtToken = generateJwtToken();
        System.out.println("Generated JWT Token: " + jwtToken);
    }
}
