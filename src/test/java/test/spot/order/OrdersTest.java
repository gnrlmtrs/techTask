package test.spot.order;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
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

    @ParameterizedTest
    @DisplayName("Test: Creating new order with the different data SPOT account")
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
    }

    @ParameterizedTest
    @DisplayName("Test: Cancel opened orders for SPOT account with a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void cancelAllOpenedOrdersTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = SendingRequest.createDeleteRequest(parameters, TestDataReader.getTestData("CANCEL_OPENED_ORDERS_URI"));
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        softAssertions.assertThat(response.getStatusCode())
                  .as("Status code is not correct")
                  .isEqualTo(Constants.CORRECT_STATUS_CODE);
        List<String> symbols = jsonPath.getList("symbol");

        softAssertions.assertThat(symbols)
                  .as("There are no opened orders for symbol " + symbol)
                  .isNotEmpty()
                  .contains(symbol)
                  .allMatch(s -> s.equals(symbol));
    }
}
