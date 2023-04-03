package test.futures.trade;

import io.restassured.response.Response;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.BaseTest;
import test.Constants;
import request.SendingRequest;
import utils.TestDataReader;

public class TradeTest extends BaseTest {

    @ParameterizedTest
    @DisplayName("Test: Change leverage for a specific symbol")
    @CsvFileSource(resources = "/testData/changingLeverage.csv")
    public void changeLeverageTest(String symbol, String leverage){
        parameters.put("symbol", symbol);
        parameters.put("leverage", leverage);
        Response response = SendingRequest.createPostRequest(parameters, TestDataReader.getTestData("CHANGE_LEVERAGE_URI"));
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        Assertions.assertEquals(symbol, response.getBody().jsonPath().get("symbol"), "Symbol is wrong");
        Assertions.assertEquals(leverage, response.getBody().jsonPath().get("leverage"), "Leverage is wrong");
    }
}
