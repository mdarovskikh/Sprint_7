package ru.yandex.praktikum.couriertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.courier.CourierCreate;
import ru.yandex.praktikum.courier.CourierLogin;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.courier.Data;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;


public class CourierLoginTests {
    private CourierCreate courierClient;
    private CourierLogin courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierCreate();
        courier = CourierLogin.getGeneratorDataCourier();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        createResponse.assertThat().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Авторизация с корректными данными")
    @Description("Проверка успешной авторизации с правильным логином и паролем")
    public void checkAuthorizationWithAllParameters() {
        ValidatableResponse response = courierClient.setCourierID(Data.getData(courier));

        response.assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());

        courierId = response.extract().path("id").toString();
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка ответа после попытки входа при login = пустое значение")
    public void checkAuthorizationWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.setCourierID(Data.getData(courier));

        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка ответа после попытки входа при password = пустое значение")
    public void checkAuthorizationWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.setCourierID(Data.getData(courier));

        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина и пароля")
    @Description("Проверка ответа после попытки входа при пустых значениях для login и password")
    public void checkAuthorizationWithoutLoginAndPassword() {
        courier.setLogin("");
        courier.setPassword("");
        ValidatableResponse response = courierClient.setCourierID(Data.getData(courier));

        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Проверка ошибки при попытке входа с login = несуществующее значение")
    public void checkAuthorizationWithInvalidLogin() {
        String correctPassword = courier.getPassword();
        courier.setLogin("invalid_login_" + System.currentTimeMillis());
        courier.setPassword(correctPassword);
        ValidatableResponse response = courierClient.setCourierID(Data.getData(courier));
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверка ошибки при попытке входа с password = несуществующее значение")
    public void checkAuthorizationWithInvalidPassword() {
        String correctLogin = courier.getLogin();
        courier.setLogin(correctLogin);
        courier.setPassword("wrong_password");
        ValidatableResponse response = courierClient.setCourierID(Data.getData(courier));
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @After
    public void tearDown() {
        if (courierId == null) {
            try {
                ValidatableResponse loginResponse = courierClient.setCourierID(Data.getData(courier));
                courierId = loginResponse.extract().path("id").toString();
            } catch (Exception e) {
                return;
            }
        }
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }
}