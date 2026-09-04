package ru.yandex.praktikum.ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.order.Order;
import ru.yandex.praktikum.order.OrderCreate;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static ru.yandex.praktikum.utils.User.*;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedTests {

    private OrderCreate orderClient;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> colour;

    public OrderCreateParameterizedTests(String firstName, String lastName, String address,
                                         String metroStation, String phone, int rentTime,
                                         String deliveryDate, String comment, List<String> colour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colour = colour;
    }

    @Before
    public void setUp() {
        orderClient = new OrderCreate();
    }

    @Parameterized.Parameters(name = "Цвет самоката: {8}")
    public static Object[][] getValueOrderCreate() {
        return new Object[][]{
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, List.of(BLACK)},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, List.of(GREY)},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, List.of(BLACK, GREY)},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, List.of()},
        };
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами цветов")
    @Description("Проверка, что заказ создаётся с любыми вариантами цветов и в ответе есть track")
    public void createOrderWithDifferentColors() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colour);
        ValidatableResponse response = orderClient.createNewOrder(order);
        response.assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }
}