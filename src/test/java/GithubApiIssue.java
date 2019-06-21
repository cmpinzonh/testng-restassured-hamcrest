import groovy.json.JsonOutput;
import groovy.json.JsonParser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class GithubApiIssue {
    @Test
    public void checkUserPublicRepos () {
        Response response =
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                get("https://api.github.com/user").
        then().
                extract().response();

        Assert.assertNotEquals(response.path("public_repos"), 0);

        String repos_url = response.path("repos_url");
        Response repo =
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
        when().
                get(repos_url).
        then().
                extract().response();

        // Para extraer un elemento particular en la lista del response
        String repoName = repo.path("[0].name");
        System.out.println(repoName);
        Assert.assertNotEquals(repoName,"undefined");


        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("title", "this is just a test");

        Response issue =
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
                contentType("application/json").
                body(jsonAsMap).
        when().
                post("https://api.github.com/repos/cmpinzonh/"+repoName+"/issues").
        then().
                extract().response();

        Assert.assertEquals(issue.path("title") , "this is just a test");
        Assert.assertNull(issue.path("body"));

        Integer issueNumber = issue.path("number");
        System.out.println(issueNumber);

        Map<String, Object> updateIssue = new HashMap<String, Object>();
        updateIssue.put("body", "body updated");
        updateIssue.put("state", "closed");

        Response updatedIssue =
        given().
                auth().oauth2(System.getenv("ACCESS_TOKEN")).
                contentType("application/json").
                body(updateIssue).
        when().
                patch("https://api.github.com/repos/cmpinzonh/"+repoName+"/issues/"+issueNumber).
        then().
                extract().response();

        Assert.assertEquals(updatedIssue.path("body" ), "body updated" );
        Assert.assertEquals(updatedIssue.path( "state "), "closed");

    }
}
