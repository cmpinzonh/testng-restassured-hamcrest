import io.restassured.http.Headers;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class GithubApiRedirect {
    private String redirectURL = "https://github.com/aperdomob/redirect-test";
    private String newURL = "https://github.com/aperdomob/new-redirect-test";
    @Test
    public void redirectTest () {


        Headers headers =
                given().
                        redirects().follow(false).
                        auth().oauth2(System.getenv("ACCESS_TOKEN")).
                when().
                        head(redirectURL).
                then().
                        statusCode(301).
                        extract().headers();

                String locationHeader = headers.get("location").getValue();
                Assert.assertEquals(locationHeader, newURL);
    }

    @Test
    public void newUrlTest () {
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                head(newURL).
        then().
                statusCode(200);
    }
}
