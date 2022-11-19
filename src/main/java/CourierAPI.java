import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class CourierAPI {
    @Step("Get courier's id")
    public static int getCourierId(CourierLoginDTO courierLoginDTO) {
        return given().header("Content-type", "application/json").body(courierLoginDTO).post("/api/v1/courier/login")
                .body().as(CourierLoginResponse.class).getId();
    }

    @Step("Delete the courier")
    public static void deleteCourier(CourierLoginDTO courierLoginDTO) {
        int courierId = getCourierId(courierLoginDTO);
        String deletionURL = String.format("/api/v1/courier/%d", courierId);
        given().delete(deletionURL);
    }

    @Step("Create a new courier")
    public static void createCourier(CourierCreationDTO creationDTO) {
        given().header("Content-type", "application/json").body(creationDTO).post("/api/v1/courier");
    }
}
