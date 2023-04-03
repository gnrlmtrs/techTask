package test.spot.order;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.BaseTest;
import test.Constants;
import utils.ApiUtils;
import utils.TestDataReader;

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
        Response response = ApiUtils.createPostRequest(parameters, TestDataReader.getTestData("CREATE_NEW_ORDER_URI"));
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }

    @ParameterizedTest
    @DisplayName("Test: Cancel opened orders for SPOT account with a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void cancelAllOpenedOrdersTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = ApiUtils.getHttpRequestWithParameters(TestDataReader.getTestData("CANCEL_OPENED_ORDERS_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }
}
