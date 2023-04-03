package test.spot.account;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.BaseTest;
import test.Constants;
import request.SendingRequest;
import utils.TestDataReader;
import java.util.List;

public class AccountTest extends BaseTest {

    @Test
    @DisplayName("Test: Get information about SPOT account")
    public void getInformationAboutAccountTest(){
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("ACCOUNT_INFO_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        ResponseBody body = response.getBody();
        Assertions.assertEquals(body.jsonPath().get("accountType"), Constants.ACCOUNT_TYPE_SPOT, "Account type is not spot");
        JsonPath jsonPath = new JsonPath(body.asString());
        List<String> balances = jsonPath.getList(Constants.PATH_TO_FIND_FREE_BALANCES);
        for (String balance : balances) {
            Assertions.assertNotNull(balance, "Balance is null");
        }
    }

    @ParameterizedTest
    @DisplayName("Test: Get all infomation about account trades for a specific symbol")
    @CsvFileSource(resources = "/testData/symbols.csv")
    public void getInformationAboutAccountTradesTest(String symbol){
        parameters.put("symbol", symbol);
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("ACCOUNT_TRADES_INFO_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        List<String> symbols = jsonPath.getList("symbol");
        for (String expectedSymbol : symbols) {
            Assertions.assertEquals(symbol, expectedSymbol, "Symbol is wrong");
        }
    }

    @ParameterizedTest
    @DisplayName("Test: Get information about opened orders")
    @CsvFileSource(resources = "/testData/creatingOrder.csv")
    public void getInformationAboutOpenedOrdersTest(String symbol){
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("CURRENT_OPENED_ORDERS_URI"), parameters);
        softAssertions.assertThat(response.getBody()).as("Body is null").isNotNull();
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        softAssertions.assertThat(response.getBody().jsonPath().get("symbol").toString()).isEqualTo(symbol);
        softAssertions.assertAll();
    }
}
