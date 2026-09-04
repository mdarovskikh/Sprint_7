package ru.yandex.praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.utils.Base;
import static io.restassured.RestAssured.given;

public class CourierCreate extends Base {

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierLogin courier) {
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier/")
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse setCourierID(Data data) {
        return given().log().all()
                .spec(getSpec())
                .body(data)
                .when()
                .post("/api/v1/courier/login/")
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String courierID) {
        return given()
                .spec(getSpec())
                .when()
                .delete("/api/v1/courier/" + courierID)
                .then().log().all();
    }
}

