package test.spot.account;

import io.restassured.response.Response;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.BaseTest;
import test.Constants;
import utils.ApiUtils;
import utils.TestDataReader;

public class AccountTest extends BaseTest {

    @Test
    @DisplayName("Test: Get information about SPOT account")
    public void getInformationAboutAccountTest(){
        Response response = ApiUtils.getHttpRequestWithParameters(TestDataReader.getTestData("ACCOUNT_INFO_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }

    @ParameterizedTest
    @DisplayName("Test: Get all infomation about account trades for a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void getInformationAboutAccountTradesTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = ApiUtils.getHttpRequestWithParameters(TestDataReader.getTestData("ACCOUNT_TRADES_INFO_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }

    @Test
    @DisplayName("Test: Get information about opened orders")
    public void getInformationAboutOpenedOrdersTest(){
        Response response = ApiUtils.getHttpRequestWithParameters(TestDataReader.getTestData("CURRENT_OPENED_ORDERS_URI"), parameters);
        softAssertions.assertThat(response.getBody()).as("Body is null").isNotNull();
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        softAssertions.assertAll();
    }
}
