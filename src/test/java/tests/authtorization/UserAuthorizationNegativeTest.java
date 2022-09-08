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

public class UserAuthorizationNegativeTest {
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
    public void checkAuthorizationUserWithWrongEmail(){
        RegistrationMethods.authorizationUserAndAssertCode(new DataUser(email + "1", password, null), (HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void checkAuthorizationUserWithWrongPassword(){
        RegistrationMethods.authorizationUserAndAssertCode(new DataUser(email, password + "1", null), (HttpStatus.SC_UNAUTHORIZED));
    }

    @AfterClass
    public static void clear(){
        String accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
        RegistrationMethods.deleteUser(accessToken, HttpStatus.SC_ACCEPTED);
    }
}
