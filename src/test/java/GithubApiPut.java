import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

    public class GithubApiPut {

        @Test
        public void followUserTest () {
            given().
                    auth().oauth2(System.getenv("ACCESS_TOKEN")).
            when().
                    put("https://api.github.com/user/following/srestrepoo").
            then().
                    statusCode(204).
                    body(equalTo(""));
        }

        @Test
        public void checkFollowingList () {
            given().
                    auth().oauth2(System.getenv("ACCESS_TOKEN")).
                    when().
                    get("https://api.github.com/user/following").
                    then().
                    statusCode(200).
                    body("login", hasItem(equalTo("srestrepoo")));
        }
}
