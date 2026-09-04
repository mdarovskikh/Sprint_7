package ru.yandex.praktikum.couriertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.courier.CourierCreate;
import ru.yandex.praktikum.courier.CourierLogin;
import ru.yandex.praktikum.courier.Data;
import static org.hamcrest.Matchers.equalTo;


public class CourierCreateTests {
    private CourierCreate courierClient;
    private CourierLogin courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierCreate();
        courier = CourierLogin.getGeneratorDataCourier();
    }

    @Test
    @DisplayName("Создание курьера с корректными данными")
    @Description("Проверка успешного создания клиента")
    public void createCourierSuccess() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        ValidatableResponse loginResponse = courierClient.setCourierID(Data.getData(courier));
        courierId = loginResponse.extract().path("id").toString();
    }


    @Test
    @DisplayName("Создание курьера только с обязательными полями")
    @Description("Проверка успешного создания курьера с пустым полем firstName")
    public void createCourierWithOutFirstName() {
        courier.setFirstName("");
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        courierResponse.assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
        ValidatableResponse setCouroerIdResponse = courierClient.setCourierID(Data.getData(courier));
        courierId = setCouroerIdResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ошибки при создании курьера без login")
    public void createCourierWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Проверка ошибки при создании курьера с существующим login")
    public void createDuplicateCourier() {
        ValidatableResponse firstResponse = courierClient.createCourier(courier);
        firstResponse.assertThat().statusCode(201);

        ValidatableResponse loginResponse = courierClient.setCourierID(Data.getData(courier));
        courierId = loginResponse.extract().path("id").toString();

        ValidatableResponse secondResponse = courierClient.createCourier(courier);
        secondResponse.assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ошибки при создании курьера без password")
    public void createCourierWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина, пароля и имени")
    @Description("Проверка ошибки при создании курьера без login, password, firtsName")
    public void createCourierWithoutLoginPasswordFirstName() {
        courier.setLogin("");
        courier.setPassword("");
        courier.setFirstName("");
        ValidatableResponse courierResponse = courierClient.createCourier(courier);
        courierResponse.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }
}
