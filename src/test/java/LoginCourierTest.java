import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;

public class LoginCourierTest {
    @Before
    public void init() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Can login courier")
    public void canLoginCourier() {
        CourierCreationDTO creationDTO = new CourierCreationDTO("akbooch@yandex.ru", "1234", "saske");
        CourierAPI.createCourier(creationDTO);
        CourierLoginDTO loginDTO = CourierLoginDTO.fromCourierCreationDTO(creationDTO);

        given().header("Content-type", "application/json").body(loginDTO).post("/api/v1/courier/login").then()
                .assertThat().statusCode(equalTo(200)).and().body("id", notNullValue());

        CourierAPI.deleteCourier(loginDTO);
    }

    @Test
    @DisplayName("Can not login courier without login field")
    public void canNotLoginCourierWithoutLogin() {
        CourierCreationDTO creationDTO = new CourierCreationDTO("akbooch@yandex.ru", "1234", "saske");
        CourierAPI.createCourier(creationDTO);
        CourierLoginDTO loginDTO = CourierLoginDTO.fromCourierCreationDTO(creationDTO);
        loginDTO.setLogin(null);

        given().header("Content-type", "application/json").body(loginDTO).post("/api/v1/courier/login").then()
                .assertThat().statusCode(equalTo(400)).and().body("message", notNullValue());

        loginDTO.setLogin(creationDTO.getLogin());
        CourierAPI.deleteCourier(loginDTO);
    }

    @Test
    @DisplayName("Can not login courier without password field")
    public void canNotLoginCourierWithoutPassword() {
        CourierCreationDTO creationDTO = new CourierCreationDTO("akbooch@yandex.ru", "1234", "saske");
        CourierAPI.createCourier(creationDTO);
        CourierLoginDTO loginDTO = CourierLoginDTO.fromCourierCreationDTO(creationDTO);
        loginDTO.setPassword(null);

        given().header("Content-type", "application/json").body(loginDTO).post("/api/v1/courier/login").then()
                .assertThat().statusCode(equalTo(400)).and().body("message", notNullValue());

        loginDTO.setPassword(creationDTO.getPassword());
        CourierAPI.deleteCourier(loginDTO);
    }

    @Test
    @DisplayName("Can not login courier with wrong login")
    public void canNotLoginCourierWithWrongLogin() {
        CourierLoginDTO loginDTO = new CourierLoginDTO("akbooch@yandex.ru", "1234");

        given().header("Content-type", "application/json").body(loginDTO).post("/api/v1/courier/login").then()
                .assertThat().statusCode(equalTo(404)).and().body("message", notNullValue());
    }

    @Test
    @DisplayName("Can not login courier with wrong password")
    public void canNotLoginCourierWithWrongPassword() {
        CourierCreationDTO creationDTO = new CourierCreationDTO("akbooch@yandex.ru", "1234", "saske");
        CourierAPI.createCourier(creationDTO);
        CourierLoginDTO loginDTO = CourierLoginDTO.fromCourierCreationDTO(creationDTO);
        loginDTO.setLogin("undefined_akbooch@yandex.ru");

        given().header("Content-type", "application/json").body(loginDTO).post("/api/v1/courier/login").then()
                .assertThat().statusCode(equalTo(404)).and().body("message", notNullValue());

        loginDTO.setPassword(creationDTO.getPassword());
        CourierAPI.deleteCourier(loginDTO);
    }
}
