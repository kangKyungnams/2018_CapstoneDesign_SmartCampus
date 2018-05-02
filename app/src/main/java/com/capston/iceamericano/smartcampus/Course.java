package com.capston.iceamericano.smartcampus;

public class Course {

    String title; // 강의 제목
    String professor_name; // 교수 이름
    String classroom_id; // 강의실
    String credit; // 강의 학점
    String lectureID; // 학수번호

    public Course(String title, String professor_name, String classroom_id, String credit, String lectureID) {
        this.title = title;
        this.professor_name = professor_name;
        this.classroom_id = classroom_id;
        this.credit = credit;
        this.lectureID = lectureID;
    }

    public String getLectureID() {
        return lectureID;
    }

    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public String getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(String classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
