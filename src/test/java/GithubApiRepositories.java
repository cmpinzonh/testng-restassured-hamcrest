import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class GithubApiRepositories {
    @Test
    public void checkUserInfo () {
        given().
                auth().preemptive().oauth2("960431b16fa8f1dd49ec5aaa44c62fa924f84380").
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
        get("https://api.github.com/users/aperdomob/repos").then().body("$.findAll { it.id == 61072663 }.name", equalTo("agile-testing-school")); // failling
    }
}
