package test.futures.order;

import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.BaseTest;
import test.Constants;
import request.SendingRequest;
import utils.TestDataReader;

import java.util.List;

public class OrdersTest extends BaseTest {

    private static final String EXPECTED_ORDER_STATUS = "NEW";

    @ParameterizedTest
    @DisplayName("Test: Creating new order with the different data")
    @CsvFileSource(resources = "/testData/creatingOrder.csv")
    public void createNewOrderTest(String symbol, String side, String timeInForce, String type, String quantity, String price){
        parameters.put("symbol", symbol);
        parameters.put("side", side);
        parameters.put("type", type);
        parameters.put("timeInForce", timeInForce);
        parameters.put("quantity", quantity);
        parameters.put("price", price);
        Response response = SendingRequest.createPostRequest(parameters, TestDataReader.getTestData("CREATE_NEW_ORDER_URI"));
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        ResponseBody body = response.getBody();
        Assertions.assertEquals(symbol, body.jsonPath().get("symbol"), "Symbol is wrong");
        Assertions.assertEquals(Double.parseDouble(price), Double.parseDouble(body.jsonPath().get("price")), "Price is wrong");
        Assertions.assertEquals(side, body.jsonPath().get("side"), "Side is wrong");
        Assertions.assertEquals(type, body.jsonPath().get("type"), "Type is wrong");
        Assertions.assertEquals(timeInForce, body.jsonPath().get("timeInForce"), "Time in force is wrong");
        Assertions.assertEquals(Double.parseDouble(quantity), Double.parseDouble(body.jsonPath().get("origQty")), "Quantity is wrong");
        Assertions.assertEquals(EXPECTED_ORDER_STATUS, body.jsonPath().get("status"), "Status is wrong");
    }

    @ParameterizedTest
    @DisplayName("Test: Get information about all orders for a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void getAllOrdersInfoTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("ALL_ORDERS_INFO_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        List<String> symbols = jsonPath.getList("symbol");
        for (String expectedSymbol : symbols) {
            Assertions.assertEquals(symbol, expectedSymbol, "Symbol is wrong");
        }
    }

    @ParameterizedTest
    @DisplayName("Test: Delete all opened orders for a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void deleteAllOpenedOrdersTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = SendingRequest.createDeleteRequest(parameters, TestDataReader.getTestData("DELETE_ALL_OPENED_ORDERS_URI"));;
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to: " + Constants.CORRECT_STATUS_CODE);
        Assertions.assertEquals(Constants.SUCCESSFULLY_DELETING_ORDERS_MESSAGE, response.getBody().jsonPath().get("msg"), "Orders weren't deleted");
    }
}
