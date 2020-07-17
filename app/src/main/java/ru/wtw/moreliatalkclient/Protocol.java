package ru.wtw.moreliatalkclient;


import java.sql.Timestamp;

public class Protocol {
    private String mode;
    private String username;
    private String password;
    private String text;
    private String timestamp;
    private String status;

    public String getMode() {
        return mode;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDescription() {
        if (status.equals("true")) return "Авторизация успешна";
        if (status.equals("false")) return "Авторизация НЕ УСПЕШНА";
        if (status.equals("newreg")) return "Новая учетная запись зарегистрирована";
        return "Незвестный ответ от сервера при авторизации";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

