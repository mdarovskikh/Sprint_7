package ru.yandex.praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.utils.Base;
import static io.restassured.RestAssured.given;

public class OrderCreate extends Base {
    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(Order order) {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getListOrders() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get("/api/v1/orders")
                .then().log().all();
    }

    @Step("Получить заказ по его номеру")
    public ValidatableResponse getOrderByNumber(String orderNumber) {
        return given().log().all()
                .spec(getSpec())
                .queryParam("t", orderNumber)
                .get("/api/v1/orders/track")
                .then().log().all();
    }

    @Step("Отменить заказ")
    public ValidatableResponse revokeOrder(String orderNumber) {
        return given().log().all()
                .spec(getSpec())
                .body(orderNumber)
                .when()
                .put("/api/v1/orders/cancel")
                .then().log().all();
    }
}
