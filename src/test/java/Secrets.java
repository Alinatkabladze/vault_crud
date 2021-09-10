import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Secrets {



    @Test
    public void getCredentials() {
        ResponseBody response = given()
                .header("X-Vault-Token", "s.IxWVbMabUce9z5d2IHYuCSGX")
                .when()
                .get("http://127.0.0.1:8200/v1/secret/data/user1")
                .then()
               .log().all()
                .extract().response().getBody();



    }
}