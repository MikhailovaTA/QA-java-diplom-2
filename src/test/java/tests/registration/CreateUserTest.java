package tests.registration;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.serialization.DataUser;
import tests.serialization.RegistrationMethods;

import static tests.serialization.Config.BASE_URL;

public class CreateUserTest {

    private static String email;
    private static String password;
    private static String name;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        password = RandomStringUtils.randomAlphabetic(8);
        name = RandomStringUtils.randomAlphabetic(8);
    }

    @Test
    public void createUser(){
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, name), (HttpStatus.SC_OK));
    }

    @AfterClass
    public static void clear(){
        String accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
        RegistrationMethods.deleteUser(accessToken, HttpStatus.SC_ACCEPTED);
    }
}
