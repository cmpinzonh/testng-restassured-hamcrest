import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class GithubAuth {
    @Test
    public void authUser()
    {
        given().
                auth().preemptive().oauth2("960431b16fa8f1dd49ec5aaa44c62fa924f84380").
                pathParam("githubUsername", "cmpinzonh").
                pathParam("repository", "workshop-api-testing-js").
        when().
                get("https://api.github.com/repos/{githubUsername}/{repository}").
        then().
                statusCode(200).
                body("description", equalTo("This is an API testing workshop"));
    }
}
