
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderCreationDTO creationDTO;

    public CreateOrderTest(OrderCreationDTO creationDTO) {
        this.creationDTO = creationDTO;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new OrderCreationDTO("akbooch@yandex.ru", "Uchiha", "Konoha, 142 apt.", 4,
                "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK"))},
                {new OrderCreationDTO("akbooch@yandex.ru", "Uchiha", "Konoha, 142 apt.", 4,
                        "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha",
                        List.of("BLACK", "GREY"))},
                {new OrderCreationDTO("akbooch@yandex.ru", "Uchiha", "Konoha, 142 apt.", 4,
                        "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", null)}};
    }

    @Before
    public void init() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Can create a new order")
    public void canCreateOrder() {
        Response response = given().header("Content-type", "application/json").body(creationDTO).post("/api/v1/orders");
        response.then().assertThat().statusCode(equalTo(201)).and().body("track", notNullValue());

        OrderTrackPOJO orderTrackPOJO = response.body().as(OrderTrackPOJO.class);
        OrdersAPI.cancelOrder(orderTrackPOJO);
    }
}
