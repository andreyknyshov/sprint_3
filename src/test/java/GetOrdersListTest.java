
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetOrdersListTest {
    @Before
    public void init() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Can get orders list")
    public void canGetOrdersList() {
        Response response = given().get("/api/v1/orders");
        response.then().assertThat().statusCode(200).and().body("availableStations", notNullValue()).and()
                .body("orders", notNullValue()).and().body("pageInfo", notNullValue());
    }
}
