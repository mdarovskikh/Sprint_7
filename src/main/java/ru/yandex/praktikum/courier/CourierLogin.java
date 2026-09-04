package ru.yandex.praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierLogin {
    private String login;
    private String password;
    private String firstName;
    private int courierID;

    public CourierLogin(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.courierID = courierID;
    }

    public CourierLogin() {

    }

    public static CourierLogin getGeneratorDataCourier() {
        String login = RandomStringUtils.randomAlphabetic(12);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierLogin(login, password, firstName);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getCourierID() {
        return courierID;
    }

    public void setCourierID(int courierID) {
        this.courierID = courierID;
    }
}

