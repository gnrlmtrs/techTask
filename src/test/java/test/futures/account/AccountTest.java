package test.futures.account;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;
import test.Constants;
import request.SendingRequest;
import utils.TestDataReader;

public class AccountTest extends BaseTest{

    private static final String ASSET_NAME = "USDT";

    @Test
    @DisplayName("Test: Get information about futures account")
    public void getAccountBalanceTest(){
        Response response = SendingRequest.getHttpRequestWithParameters(TestDataReader.getTestData("GET_BALANCE_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
        String usdtBalance = response.jsonPath().getString(String.format(Constants.PATH_TO_FIND_BALANCE, ASSET_NAME));
        Assertions.assertTrue(Double.parseDouble(usdtBalance) > 0, "USDT balance is less than zero");
    }
}
