package ru.yandex.praktikum.ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.order.OrderCreate;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class OrderGetTests {
    OrderCreate orderCreate = new OrderCreate();

    @Test
    @DisplayName("Получение заказа без указания номера заказа")
    @Description("Проверка ответа при попытке получения заказа без номера заказа")
    public void checkOrderGetTrackWithoutId() {
        String trackOrder = "";
        ValidatableResponse response = orderCreate.getOrderByNumber(trackOrder);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Получение заказа по несуществующему номеру заказа")
    @Description("Проверка ответа при попытке получения заказа с несуществующим номером заказа")
    public void checkOrderGetTrackWithNotRegistered() {
        String trackOrder = "666666";
        ValidatableResponse response = orderCreate.getOrderByNumber(trackOrder);
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Заказ не найден"));
    }
}

