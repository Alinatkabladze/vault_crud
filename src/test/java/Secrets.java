import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Secrets {


    @Test
    public void getCredentialsFromSecret() {
        ResponseBody response = given()
                .header("X-Vault-Token", "s.RdRqki9cnxkUrnpWLe2SzLQP")
                .when()
                .get("http://127.0.0.1:8200/v1/secret/data/user1")
                .then()
               .log().all()
                .extract().response().getBody();
        /*JsonPath jsonPath=response.jsonPath();
        jsonPath.get("request_id");
        System.out.println(jsonPath)*/;

    }

    @Test
    public void getCredentialsFromKV() {
        ResponseBody response = given()
                .header("X-Vault-Token", "s.RdRqki9cnxkUrnpWLe2SzLQP")
                .when()
                .get("http://127.0.0.1:8200/v1/kv/data/user")

                .then()
                .log().all()
                .extract().response().getBody();

    }
    @Test
    public void updateCredentialsFromKV() {

        Map<String,Object> jsonBodyUsingMap = new HashMap<String,Object>();
        Map<String,String> bookingDatesMap = new HashMap<String,String>();
        bookingDatesMap.put("login1", "alina");
        bookingDatesMap.put("password1", "2021");

        jsonBodyUsingMap.put("data", bookingDatesMap);

            ResponseBody response = given()
                    .header("X-Vault-Token", "s.RdRqki9cnxkUrnpWLe2SzLQP")
                    .header("Content-Type", "application/json")
                    .body(jsonBodyUsingMap)
                    .when()
                    .post("http://127.0.0.1:8200/v1/secret/data/user")
                    .then()
                    .log().all()
                    .extract().response().getBody();
        }
    }
