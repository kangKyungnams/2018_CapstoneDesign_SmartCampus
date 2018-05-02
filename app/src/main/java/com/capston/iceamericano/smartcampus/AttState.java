package com.capston.iceamericano.smartcampus;

public class AttState {

    int num; // 강의 제목
    String name; // 교수 이름
    String date; // 강의실
    String state_Check; // 강의 학점

    public AttState(int num, String name, String date, String state_Check) {
        this.num = num;
        this.name = name;
        this.date = date;
        this.state_Check = state_Check;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState_Check() {
        return state_Check;
    }

    public void setState_Check(String state_Check) {
        this.state_Check = state_Check;
    }
}
