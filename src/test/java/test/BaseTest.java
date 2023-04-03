package test;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.HashMap;

public abstract class BaseTest {

    protected HashMap<String, String> parameters;
    protected SoftAssertions softAssertions;

    @BeforeEach
    private void setUp(){
        softAssertions = new SoftAssertions();
        parameters = new HashMap<>();
    }

    @AfterEach
    private void cleanUp(){
        softAssertions = null;
        parameters.clear();
    }
}
