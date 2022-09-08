package tests.serialization;

import tests.deserialization.ServerResponseAfterAuthorization;

import static io.restassured.RestAssured.given;

public class RegistrationMethods {

    private static final String CREATE_USER_METHOD = "/api/auth/register";
    private static final String LOGIN_USER_METHOD = "/api/auth/login";
    private static final String UPDATE_USER_METHOD = "/api/auth/user";
    private static final String DELETE_USER_METHOD = "/api/auth/user";

    public static void createUserAndAssertCode(DataUser dataUser, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .body(dataUser)
            .post(CREATE_USER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static void authorizationUserAndAssertCode(DataUser dataUser, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .body(dataUser)
            .post(LOGIN_USER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static String authorizationUser(DataUser dataUser){
        ServerResponseAfterAuthorization responceServer = given()
            .header("Content-type", "application/json")
            .body(dataUser)
            .post(LOGIN_USER_METHOD)
            .body()
            .as(ServerResponseAfterAuthorization.class);
        return responceServer.getAccessToken();
    }

    public static void changeUserDateAndAssertCode(DataUser dataUser, String accessToken, int expectedCode){
        given()
           .header("Content-type", "application/json")
            .auth().oauth2(accessToken.replace("Bearer ", ""))
            .and()
            .body(dataUser)
            .patch(UPDATE_USER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static void changeUserDateWithoutToken(DataUser dataUser, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .body(dataUser)
            .patch(UPDATE_USER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }

    public static void deleteUser(String accessToken, int expectedCode){
        given()
            .header("Content-type", "application/json")
            .auth().oauth2(accessToken.replace("Bearer ", ""))
            .delete(DELETE_USER_METHOD)
            .then()
            .assertThat()
            .statusCode(expectedCode);
    }
}
