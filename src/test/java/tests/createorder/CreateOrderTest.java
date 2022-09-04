package tests.createorder;

import io.restassured.RestAssured;
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

public class CreateOrderTest {

    private static String email;
    private static String password;
    private static String accessToken;
    private static List<Ingredient> ingredientsList;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        password = RandomStringUtils.randomAlphabetic(8);
        RegistrationMethods.createUserAndAssertCode(new DataUser(email, password, "Lando"), HttpStatus.SC_OK);
        accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
        ingredientsList = CreateOrderMethods.getIngredientList();
    }

    @Test
    public void checkCreateOrderWithoutToken(){
        List<String> ingredients = new ArrayList();
        ingredients.add(ingredientsList.get(1).get_id());
        ingredients.add(ingredientsList.get(2).get_id());
        DataOrder dataOrder = new DataOrder(ingredients);
        CreateOrderMethods.createOrderAndAssertCode(dataOrder, HttpStatus.SC_OK);
    }

    @Test
    public void checkCreateOrderWithToken(){
        List<String> ingredients = new ArrayList();
        ingredients.add(ingredientsList.get(1).get_id());
        ingredients.add(ingredientsList.get(2).get_id());
        DataOrder dataOrder = new DataOrder(ingredients);
        CreateOrderMethods.createOrderWithTokenAndAssertCode(dataOrder, accessToken, HttpStatus.SC_OK);
    }

    @Test
    public void checkCreateOrderWithoutIngredient(){
        List<String> ingredients = new ArrayList();
        DataOrder dataOrder = new DataOrder(ingredients);
        CreateOrderMethods.createOrderWithTokenAndAssertCode(dataOrder, accessToken, HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void checkCreateOrderWithWrongIdIngredient(){
        List<String> ingredients = new ArrayList();
        ingredients.add("123456789012345678901234");
        DataOrder dataOrder = new DataOrder(ingredients);
        CreateOrderMethods.createOrderWithTokenAndAssertCode(dataOrder, accessToken, HttpStatus.SC_BAD_REQUEST);
    }

    @AfterClass
    public static void clear(){
        String accessToken = RegistrationMethods.authorizationUser(new DataUser(email, password, null));
        RegistrationMethods.deleteUser(accessToken, HttpStatus.SC_ACCEPTED);
    }
}
