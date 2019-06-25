import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class GithubApiRepositories {

    @Test
    public void checkUserInfo () {

        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                get("https://api.github.com/users/aperdomob").
        then().
                statusCode(200).
                body("name", equalTo("Alejandro Perdomo")).
                body("location", equalTo("Colombia")).
                body("company", equalTo("PSL"));
    }

    @Test
    public void checkUserRepo () {
        String expectedRepo = "workshop-api-testing-js";
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                get("https://api.github.com/users/cmpinzonh/repos").
        then().
                statusCode(200).
                body("name", hasItem(equalTo(expectedRepo)));
    }

}
