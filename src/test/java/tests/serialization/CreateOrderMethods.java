package tests.serialization;

import tests.deserialization.Ingredient;
import tests.deserialization.IngredientsResponse;
import tests.deserialization.ServerResponseAfterAuthorization;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateOrderMethods {

    private static final String CREATE_ORDER_METHOD = "/api/orders";
    private static final String GET_INGREDIENT_METHOD = "/api/ingredients";
    private static final String GET_ORDER_LIST_METHOD = "/api/orders";

    public static void createOrderAndAssertCode(DataOrder ingredients, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .body(ingredients)
            .post(CREATE_ORDER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static void createOrderWithTokenAndAssertCode(DataOrder ingredients, String accessToken, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .auth().oauth2(accessToken.replace("Bearer ", ""))
            .and()
            .body(ingredients)
            .post(CREATE_ORDER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static List<Ingredient> getIngredientList(){
        IngredientsResponse ingredientsResponse = given()
            .header("Content-type", "application/json")
            .get(GET_INGREDIENT_METHOD)
            .body()
            .as(IngredientsResponse.class);
        return ingredientsResponse.getData();
}

    public static void getOrderListWithoutTokenAndAssertCode(int expectedCode){
        given()
            .header("Content-type", "application/json")
            //.auth().oauth2(accessToken.replace("Bearer ", ""))
            .get(GET_ORDER_LIST_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static void getOrderListWithTokenAndAssertCode(String accessToken, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .auth().oauth2(accessToken.replace("Bearer ", ""))
            .get(GET_ORDER_LIST_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }
}
