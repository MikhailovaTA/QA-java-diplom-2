package tests.registration;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.serialization.DataUser;
import tests.serialization.RegistrationMethods;

import static tests.serialization.Config.BASE_URL;

public class CreateUserNegativeTest {
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
    public void createDoubleUser(){
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, name), (HttpStatus.SC_OK));
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, name), (HttpStatus.SC_FORBIDDEN));
    }

    @Test
    public void createUserWithoutPassword(){
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, null, name), (HttpStatus.SC_FORBIDDEN));
    }

    @Test
    public void createUserWithoutEmail(){
        RegistrationMethods.createUserAndAssertCode(new DataUser(null, password, name), (HttpStatus.SC_FORBIDDEN));
    }
}
