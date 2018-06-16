package com.capston.iceamericano.smartcampus;

public class Restaurant_order {

    String menu_name;
    String status;
    int num;
    String high_level;
    String restaurant_name;
    String order_date;
    String email;
    int count;

    public Restaurant_order(String menu_name, String status, int num, String high_level, String restaurant_name, String order_date, String email, int count) {
        this.menu_name = menu_name;
        this.status = status;
        this.num = num;
        this.high_level = high_level;
        this.restaurant_name = restaurant_name;
        this.order_date = order_date;
        this.email = email;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHigh_level() {
        return high_level;
    }

    public void setHigh_level(String high_level) {
        this.high_level = high_level;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getDate() {
        return order_date;
    }

    public void setDate(String order_date) {
        this.order_date = order_date;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
