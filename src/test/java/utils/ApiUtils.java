package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import logger.Log;
import java.util.HashMap;

public class ApiUtils {

    public static Response getHttpRequestWithParameters(String partOfUri, HashMap<String, String> parameters){
        Log.info("Sending get request with parameters to: " + partOfUri);
        parameters.put("recvWindow", "60000");
        return RestAssured.given()
                .header("X-MBX-APIKEY", TestDataReader.getTestData("API_KEY"))
                .when()
                .get(ParamsTransformation.getFinalURL(parameters,partOfUri));
    }

    public static Response createPostRequest(HashMap<String, String> parameters, String partOfUri){
        Log.info("Sending post request to: " + partOfUri);
        return RestAssured.given()
                .header("X-MBX-APIKEY", TestDataReader.getTestData("API_KEY"))
                .and()
                .post(ParamsTransformation.getFinalURL(parameters, partOfUri))
                .then()
                .extract().response();
    }
}
