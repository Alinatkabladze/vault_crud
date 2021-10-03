package Steps;

import Models.User;
import io.restassured.response.*;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

public class Secrets {
    private static final String BASE_URL = "http://127.0.0.1:8200/v1";


    public User getCredentials(String secretPath, String envVar) {
        baseURI=BASE_URL;
        User user=new User();
        Response response = given()
                .header("X-Vault-Token",System.getenv(envVar))
                .when()
                .get(secretPath);
        Map<String,String> data=response.jsonPath().get("data.data");
        user.setUserName(data.get("login"));
        user.setPassword(data.get("password"));
        return user;
    }

    public void createOrUpdateCredentials(String secretPath,String envVar,User user) {
        baseURI=BASE_URL;
        Map<String,Object> data = new HashMap<>();
        data.put("data", user);

        given()
                .header("X-Vault-Token", System.getenv(envVar))
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .post(secretPath)
                .then()
                .log().all();
    }

    public void deleteCredentials(String secretPath,String envVar) {
        baseURI=BASE_URL;
        given()
                .header("X-Vault-Token", System.getenv(envVar))
                .header("Content-Type", "application/json")
                .when()
                .delete(secretPath)
                .then()
                .log().all();
    }
}
