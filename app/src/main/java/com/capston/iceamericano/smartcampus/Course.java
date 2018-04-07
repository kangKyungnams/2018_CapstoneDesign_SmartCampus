package com.capston.iceamericano.smartcampus;

/**
 * Created by cksdn on 2018-03-25.
 */

public class Course {

    int courseID; //강의 고유 번호
    int courseYear; // 강의 년도
    String courseSemester; // 강의 학기
    String courseMajor; // 강의 해당 학과
    String courseGrade; // 강의 해당 학년
    String courseTitle; // 강의 제목
    String courseProfessor; // 강의 교수
    String courseTime; // 강의 시간대
    String courseRoom; // 강의실번호
    int courseCredit; // 강의 학점
    int courseDivide; // 강의 분반
    int coursePersonnel; //강의 해당 인원

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseSemester() {
        return courseSemester;
    }

    public void setCourseSemester(String courseSemester) {
        this.courseSemester = courseSemester;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseDivide() {
        return courseDivide;
    }

    public void setCourseDivide(int courseDivide) {
        this.courseDivide = courseDivide;
    }

    public int getCoursePersonnel() {
        return coursePersonnel;
    }

    public void setCoursePersonnel(int coursePersonnel) {
        this.coursePersonnel = coursePersonnel;
    }

    public Course(int courseID, int courseYear, String courseSemester, String courseMajor, String courseGrade, String courseTitle, String courseProfessor, String courseTime, String courseRoom, int courseCredit, int courseDivide, int coursePersonnel) {
        this.courseID = courseID;
        this.courseYear = courseYear;
        this.courseSemester = courseSemester;
        this.courseMajor = courseMajor;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
    }
}
