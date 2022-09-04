package tests.getorderlist;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.deserialization.Ingredient;
import tests.serialization.CreateOrderMethods;
import tests.serialization.DataOrder;
import tests.serialization.DataUser;
import tests.serialization.RegistrationMethods;

import java.util.ArrayList;
import java.util.List;

import static tests.serialization.Config.BASE_URL;

public class GetOrderTest {

    private static String email;
    private static String password;
    private static String name;
    private static String accessToken;
    private static List<Ingredient> ingredientsList;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        password = RandomStringUtils.randomAlphabetic(8);
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, "Lando"), HttpStatus.SC_OK);
        accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
        ingredientsList = CreateOrderMethods.getIngredientList();
        List<String> ingredients = new ArrayList();
        ingredients.add(ingredientsList.get(1).get_id());
        ingredients.add(ingredientsList.get(2).get_id());
        DataOrder dataOrder = new DataOrder(ingredients);
        CreateOrderMethods.createOrderWithTokenAndAssertCode(dataOrder, accessToken, HttpStatus.SC_OK);
    }

    @Test
    public void checkOrderListWithoutToken(){
        CreateOrderMethods.getOrderListWithoutTokenAndAssertCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void checkOrderListWithToken(){
        CreateOrderMethods.getOrderListWithTokenAndAssertCode(accessToken, HttpStatus.SC_OK);
    }

    @AfterClass
    public static void clear(){
        RegistrationMethods.deleteUser(accessToken, HttpStatus.SC_ACCEPTED);
    }
}
