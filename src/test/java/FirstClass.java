import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class FirstClass {
    @Test
    public void firstTest() {
        when().
            get("https://httpbin.org/ip").
        then().
            statusCode(200);
    }

    @Test
    public void contentTest() {
        when().
                get("https://httpbin.org/ip").
        then().
                contentType("application/json").
                body("origin", equalTo("181.143.53.170, 181.143.53.170") );

    }

    @Test
    public void paramTest() {
        given().
                param("name", "John").
                param("age", "31").
                param("city", "New York").
        when().
                get("https://httpbin.org/get").
        then().
                statusCode(200).
                body("$", hasKey("url")). // Check the body contains property url
                body("args", hasKey("name"));
    }

    @Test
    public void deleteTest() {
        when().
                delete("https://httpbin.org/delete").
        then().
                statusCode(200).
                body("$", hasKey("origin")).
                body("$", hasKey("json")).
                body(equalTo("{}"));
    }

    @Test
    public void headTest() {
        when().
                head("https://httpbin.org/headers").
        then().
                statusCode(200).
                body(equalTo(""));
    }
}
