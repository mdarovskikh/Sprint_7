package ru.yandex.praktikum.ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.order.OrderCreate;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    private final OrderCreate orderCreate = new OrderCreate();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка ответа с возвращением списка заказов")
    public void getOrderlist() {
        ValidatableResponse response = orderCreate.getListOrders();
        response.assertThat().log().all()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}

