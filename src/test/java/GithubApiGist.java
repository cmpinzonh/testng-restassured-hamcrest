import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class GithubApiGist {
    @Test
    public void createGist () {

        String gistContent = "function wait(method, time){ return new Promise (); }";

        Map<String, Object> gistFiles = new HashMap<String, Object>();
        gistFiles.put("promise.js", gistContent);

        Map<String, Object> newGist = new HashMap<String, Object>();
        newGist.put("description", "example promise");
        newGist.put("public", true);
        newGist.put("files","{}");

        Response response =
                given().
                        auth().oauth2(System.getenv("ACCESS_TOKEN")).
                        contentType("application/json").
                        body(newGist).
                when().
                        post("https://api.github.com/gists").
                then().statusCode(201).
                        extract().response();

        Assert.assertEquals(response.path("public"), true);
        Assert.assertEquals(response.path("description"), "example promise");

    }
}
