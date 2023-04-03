package test.futures.order;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.BaseTest;
import test.Constants;
import request.SendingRequest;
import utils.TestDataReader;

public class OrdersTest extends BaseTest {

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
    }

    @ParameterizedTest
    @DisplayName("Test: Get information about all orders for a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void getAllOrdersInfoTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("ALL_ORDERS_INFO_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }

    @ParameterizedTest
    @DisplayName("Test: Delete all opened orders for a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void deleteAllOpenedOrdersTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("DELETE_ALL_OPENED_ORDERS_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }
}
