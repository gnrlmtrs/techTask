package test.futures.account;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;
import test.Constants;
import utils.ApiUtils;
import utils.TestDataReader;

public class AccountTest extends BaseTest{

    @Test
    @DisplayName("Test: Get information about futures account")
    public void getAccountBalanceTest(){
        Response response = ApiUtils.getHttpRequestWithParameters(TestDataReader.getTestData("GET_BALANCE_URI"), parameters);
        Assertions.assertEquals(response.getStatusCode(), Constants.CORRECT_STATUS_CODE,
                "Status code is equal to" + Constants.CORRECT_STATUS_CODE);
    }
}
