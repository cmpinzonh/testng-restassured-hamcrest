import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GithubApiUsers {

    @Test
    public void responseTimeTest () {
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                get("https://api.github.com/users").
        then().
                statusCode(200).
                time(lessThan(5000L));
    }

    @Test
    public void standardQueryLengthTest () {
        Response response =
                given().
                        auth().oauth2(System.getenv("ACCESS_TOKEN")).
                when().
                        get("https://api.github.com/users").
                then().
                        statusCode(200).
                        extract().response();

        ArrayList responseLogin = response.path("login");
        Integer responseLength = responseLogin.size();
        Integer expectedLength = 30;

        Assert.assertEquals(responseLength, expectedLength);

    }

    @Test
    public void tenUsersQueryLengthTest () {
        Response response =
                given().
                        auth().oauth2(System.getenv("ACCESS_TOKEN")).
                        queryParam("per_page", "10").
                when().
                        get("https://api.github.com/users").
                then().
                        statusCode(200).
                        extract().response();

        ArrayList responseLogin = response.path("login");
        Integer responseLength = responseLogin.size();
        Integer expectedLength = 10;

        Assert.assertEquals(responseLength, expectedLength);
    }

    @Test
    public void fiftyUsersQueryLengthTest () {
        Response response =
                given().
                        auth().oauth2(System.getenv("ACCESS_TOKEN")).
                        queryParam("per_page", "50").
                        when().
                        get("https://api.github.com/users").
                        then().
                        statusCode(200).
                        extract().response();

        ArrayList responseLogin = response.path("login");
        Integer responseLength = responseLogin.size();
        Integer expectedLength = 50;

        Assert.assertEquals(responseLength, expectedLength);
    }
}
