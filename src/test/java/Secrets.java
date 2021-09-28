import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Secrets {

//s.bebEHyI1n1Zv5rQwI2H1Zg6h
    @Test
    public void getCredentialsFromSecret() {
        ResponseBody response = given()
                .header("X-Vault-Token", "s.y3MGvAyRAKjxzXfZglxaPVl1")
                .when()
                .get("http://127.0.0.1:8200/v1/cubbyhole/data/user2")
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
    public void createToken() {

        Map<String,Object> credentialsMap = new HashMap<>();
        Map<String,String> meta = new HashMap<>();
        meta.put("user", "alina");

        credentialsMap.put("ttl", "1h");
        credentialsMap.put("renewable", "true");
       credentialsMap.put("policies", "read"); //- by default policy was root

        ResponseBody response = given()
                .header("X-Vault-Token", "s.u7Cp5Q905cuQwzZbg8mHvTJ9")
                .header("Content-Type", "application/json")
                .body(credentialsMap)
                .when()
                .post("http://127.0.0.1:8200/v1/auth/token/create")// user2 - new user in secret folder
                .then()
                .log().all()
                .extract().response().getBody();
    }
    @Test
    public void initToken() {

        Map<String,Integer> credentialsMap = new HashMap<>();


        credentialsMap.put("secret_shares", 1);
        credentialsMap.put("secret_threshold", 1);
        //   credentialsMap.put("policies", "web"); - by default policy was root

        ResponseBody response = given()

                .header("Content-Type", "application/json")
                .body(credentialsMap)
                .when()
                .post("http://127.0.0.1:8200/v1/sys/init")
                .then()
                .log().all()
                .extract().response().getBody();
    }
    @Test
    public void renewToken() {

        Map<String,String> meta = new HashMap<>();
        meta.put("token", "s.y3MGvAyRAKjxzXfZglxaPVl1");



        ResponseBody response = given()
                .header("X-Vault-Token", "s.y3MGvAyRAKjxzXfZglxaPVl1")
                .header("Content-Type", "application/json")
                .body(meta)
                .when()
                .post("http://127.0.0.1:8200/v1/auth/token/renew")// user2 - new user in secret folder
                .then()
                .log().all()
                .extract().response().getBody();
    }
    @Test
    public void createCredentialsInSecret() {
        String generatedString = RandomStringUtils.randomAlphabetic(10);
        Map<String,Object> credentialsMap = new HashMap<>();
        Map<String,String> credentials = new HashMap<>();
        credentials.put("login", "alina");
        credentials.put("password", generatedString);
        credentialsMap.put("data", credentials);

            ResponseBody response = given()
                    .header("X-Vault-Token", "s.y3MGvAyRAKjxzXfZglxaPVl1")
                    .header("Content-Type", "application/json")
                    .body(credentialsMap)
                    .when()
                    .post("http://127.0.0.1:8200/v1/cubbyhole/data/user2")// user2 - new user in secret folder
                    .then()
                    .log().all()
                    .extract().response().getBody();
        }
        //for updating put an existing secret in url and change data
    @Test
    public void updateCredentialsInSecret() {

        Map<String,Object> jsonBodyUsingMap = new HashMap<String,Object>();
        Map<String,String> bookingDatesMap = new HashMap<String,String>();
        bookingDatesMap.put("login1", "alina");
        bookingDatesMap.put("password1", "2030");
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
    @Test
    public void deleteCredentialsFromSecret() {


        ResponseBody response = given()
                .header("X-Vault-Token", "s.RdRqki9cnxkUrnpWLe2SzLQP")
                .header("Content-Type", "application/json")
                .when()
                .delete("http://127.0.0.1:8200/v1/secret/data/user")
                .then()
                .log().all()
                .extract().response().getBody();
    }
    }
