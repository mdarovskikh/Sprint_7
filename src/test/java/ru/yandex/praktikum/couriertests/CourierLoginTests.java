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


public class CourierLoginTests {
    private CourierCreate courierClient;
    private CourierLogin courier;
    private String courierId;

    @Before
    public void setUp() {
        courierClient = new CourierCreate();
        courier = CourierLogin.getGeneratorDataCourier();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        createResponse.assertThat().statusCode(201);
        ValidatableResponse loginResponse = courierClient.setCourierID(Data.getData(courier));
        courierId = loginResponse.extract().path("id").toString();
    }


    @Test
    @DisplayName("Авторизация со всеми обязательными полями")
    @Description("Проверка ответа после входа в систему")
    public void checkAuthorizationWithAllParameters() {
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Data.getData(courier));
        courierId = setCourierIdResponse.extract().path("id").toString();
        setCourierIdResponse.assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка ответа после попытки входа при login = пустое значение")
    public void checkAuthorizationWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Data.getData(courier));
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка ответа после попытки входа при password = пустое значение")
    public void checkAuthorizationWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Data.getData(courier));
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина и пароля")
    @Description("Проверка ответа после попытки входа при пустых значениях для ligin и password")
    public void checkAuthorizationWithoutLoginAndPassword() {
        courier.setLogin("");
        courier.setPassword("");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Data.getData(courier));
        setCourierIdResponse.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с несуществующей парой логина и пароля")
    @Description("Проверка ответа после попытки входа при заполнении login и password несуществующими значениями")
    public void checkAuthorizationWithNotRegisteredLoginAndPassword() {
        courier.setLogin("null");
        courier.setPassword("null");
        ValidatableResponse setCourierIdResponse = courierClient.setCourierID(Data.getData(courier));
        setCourierIdResponse.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }
}
