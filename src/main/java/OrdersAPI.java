import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class OrdersAPI {
    @Step("Cancel the order")
    public static void cancelOrder(OrderTrackPOJO orderTrackPOJO) {
        given().header("Content-type", "application/json").body(orderTrackPOJO).put("/api/v1/orders/cancel");
    }
}
