package tests.update;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.serialization.DataUser;
import tests.serialization.RegistrationMethods;

import static tests.serialization.Config.BASE_URL;

public class ChangeUsersDateTest {

    private static String email;
    private static String password;
    private static String accessToken;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        password = RandomStringUtils.randomAlphabetic(8);
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, "Lando"), HttpStatus.SC_OK);
        accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
    }

    @Test
    public void checkUpdateUsersName(){
        RegistrationMethods.changeUserDateAndAssertCode(new DataUser(null, null, "Karlos"), accessToken, HttpStatus.SC_OK);
    }

    @Test
    public void checkUpdateUsersPassword(){
        RegistrationMethods.changeUserDateAndAssertCode(new DataUser(null, password + "123", null), accessToken, HttpStatus.SC_OK);
    }

    @Test
    public void checkUpdateUsersEmail(){
        RegistrationMethods.changeUserDateAndAssertCode(new DataUser("56" + email, null, null), accessToken, HttpStatus.SC_OK);
    }

    @AfterClass
    public static void clear(){
        RegistrationMethods.deleteUser(accessToken, HttpStatus.SC_ACCEPTED);
    }
}
