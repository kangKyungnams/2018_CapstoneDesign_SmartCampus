package com.capston.iceamericano.smartcampus;

public class foodMenu {
    String restaurant_name;
    String menu_name;
    String price;
    String date;
    String time_section;

    public foodMenu(String restaurant_name, String menu_name, String price, String date, String time_section) {
        this.restaurant_name = restaurant_name;
        this.menu_name = menu_name;
        this.price = price;
        this.date = date;
        this.time_section = time_section;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_section() {
        return time_section;
    }

    public void setTime_section(String time_section) {
        this.time_section = time_section;
    }
}
