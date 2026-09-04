package ru.yandex.praktikum.courier;

public class Data {
    private String login;
    private String password;

    public Data(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public Data() {

    }
    public static Data getData(CourierLogin courier) {
        return new Data(courier.getLogin(), courier.getPassword());
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
}
