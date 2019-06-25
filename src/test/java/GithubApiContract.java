import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GithubApiContract {
    @Test
    public void schemaValidationTest () {

        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                get("https://api.github.com/events").
        then().
                assertThat().body(matchesJsonSchemaInClasspath("events-schema.json"));

    }
}
