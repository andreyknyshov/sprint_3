import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;

public class CreateCourierTest {
    @Before
    public void init() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Can create a new courier")
    public void canCreateCourier() {
        CourierCreationDTO courier = new CourierCreationDTO("akbooch@yandex.ru", "1234", "saske");
        given().header("Content-type", "application/json").body(courier).post("/api/v1/courier").then().assertThat()
                .statusCode(equalTo(201)).and().body("ok", equalTo(true));

        CourierAPI.deleteCourier(CourierLoginDTO.fromCourierCreationDTO(courier));
    }

    @Test
    @DisplayName("Can not create two similar couriers")
    public void cannotCreateSimilarCouriers() {
        CourierCreationDTO courier = new CourierCreationDTO("akbooch@yandex.ru", "1234", "saske");
        given().header("Content-type", "application/json").body(courier).post("/api/v1/courier");
        given().header("Content-type", "application/json").body(courier).post("/api/v1/courier").then().assertThat()
                .statusCode(equalTo(409)).and().body("message", notNullValue());

        CourierAPI.deleteCourier(CourierLoginDTO.fromCourierCreationDTO(courier));
    }

    @Test
    @DisplayName("Can not create a new courier without password field")
    public void cannotCreateCourierWithoutPassword() {
        CourierCreationDTO courier = new CourierCreationDTO("akbooch@yandex.ru", null, "saske");
        given().header("Content-type", "application/json").body(courier).post("/api/v1/courier").then().assertThat()
                .statusCode(equalTo(400)).and().body("message", notNullValue());
    }

    @Test
    @DisplayName("Can not create a new courier without login field")
    public void cannotCreateCourierWithoutLogin() {
        CourierCreationDTO courier = new CourierCreationDTO(null, "1234", "saske");
        given().header("Content-type", "application/json").body(courier).post("/api/v1/courier").then().assertThat()
                .statusCode(equalTo(400)).and().body("message", notNullValue());
    }
}
