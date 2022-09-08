package tests.authtorization;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.serialization.DataUser;
import tests.serialization.RegistrationMethods;

import static tests.serialization.Config.BASE_URL;

public class UserAuthorizationTest {

    private static String email;
    private static String password;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        password = RandomStringUtils.randomAlphabetic(8);
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, "Lando"), HttpStatus.SC_OK);
    }

    @Test
    public void checkAuthorizationUser(){
        RegistrationMethods.authorizationUserAndAssertCode(new DataUser(email, password, null), (HttpStatus.SC_OK));
    }

    @AfterClass
    public static void clear(){
        String accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
        RegistrationMethods.deleteUser(accessToken, HttpStatus.SC_ACCEPTED);
    }
}
