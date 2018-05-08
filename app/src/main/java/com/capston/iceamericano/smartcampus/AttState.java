package com.capston.iceamericano.smartcampus;

public class AttState {

    int num; // 강의 순번
    String name; // 강의 이름
    String date; // 강의 날짜
    String state_Check; // 출석 체크
    String lectureDateKey; // 강의 출석별 시간
    String lectureID; // 강의 ID

    public String getLectureID() {
        return lectureID;
    }

    public AttState(int num, String name, String date, String state_Check, String lectureDateKey, String lectureID) {
        this.num = num;
        this.name = name;
        this.date = date;
        this.state_Check = state_Check;
        this.lectureDateKey = lectureDateKey;
        this.lectureID = lectureID;
    }

    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }

    public String getLectureDateKey() {
        return lectureDateKey;
    }

    public void setLectureDateKey(String lectureDateKey) {
        this.lectureDateKey = lectureDateKey;
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
