package ru.yandex.praktikum.couriertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import ru.yandex.praktikum.courier.CourierCreate;
import ru.yandex.praktikum.courier.CourierLogin;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.courier.Data;
import static org.hamcrest.Matchers.equalTo;

public class CourierDeleteTests {
    private CourierCreate courierClient;
    private CourierLogin courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierCreate();
    }

    @Test
    @DisplayName("Удаление курьера")
    @Description("Проверка ответа при успешном удалении курьера")
    public void checkDeleteCourier() {
        courier = CourierLogin.getGeneratorDataCourier();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        createResponse.assertThat().statusCode(201);
        ValidatableResponse loginResponse = courierClient.setCourierID(Data.getData(courier));
        courierId = loginResponse.extract().path("id").toString();
        ValidatableResponse deleteResponse = courierClient.deleteCourier(courierId);
        deleteResponse.assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Удаление курьера без ID")
    @Description("Проверка ответа при удалении курьера без указания ID")
    public void checkDeleteCourierWithoutId() {
        ValidatableResponse deleteResponse = courierClient.deleteCourier("");
        deleteResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим ID")
    @Description("Проверка ответа при удалении курьера с несуществующим ID")
    public void checkDeleteCourierWithInvalidId() {
        String invalidId = "666666";
        ValidatableResponse deleteResponse = courierClient.deleteCourier(invalidId);
        deleteResponse.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id нет."));
    }
    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }
}
