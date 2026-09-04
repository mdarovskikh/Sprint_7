package ru.yandex.praktikum.ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.order.OrderCreate;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class OrderListTest {
    private final OrderCreate orderCreate = new OrderCreate();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка ответа с возвращением списка заказов")
    public void getOrderlist() {
        ValidatableResponse response = orderCreate.getListOrders();
        response.assertThat().log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
    @Test
    @DisplayName("Получение заказа по несуществующему ID")
    @Description("Проверка ответа при попытке получения заказа с несуществующим номером заказа")
    public void getOrderByNonExistentId() {
        String nonExistentOrderId = "666666";
        ValidatableResponse response = orderCreate.getOrderByNumber(nonExistentOrderId);
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Курьер с идентификатором 666666 не найден"));
    }
}

