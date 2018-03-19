/**
 * Created by cksdn on 2018-03-18.
 */

public class Course {
    int courseID; //강의 고유번호
    String StuPro; //학생,교수 구분
    int courseYear; //강의 해당 년도
    String courseTerm; // 강의 해당 학기
    String courseMajor; // 강의 해당 학과
    String courseArea; //강의 종류(교양인지 전공인지)
    String courseGrade; // 강의 해당 학년
    String courseTitle; // 강의 제목
    int courseCredit; // 강의 학점
    int courseDivide; // 강의 분반
    int coursePersonnel; // 강의 인원
    String courseProfessor; // 강의 교수
    String courseTime; //강의 시간대
    String courseRoom; //강의실

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStuPro() {
        return StuPro;
    }

    public void setStuPro(String stuPro) {
        StuPro = stuPro;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
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

    public Course(int courseID, String stuPro, int courseYear, String courseTerm, String courseMajor, String courseArea, String courseGrade, String courseTitle, int courseCredit, int courseDivide, int coursePersonnel, String courseProfessor, String courseTime, String courseRoom) {
        this.courseID = courseID;
        StuPro = stuPro;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseMajor = courseMajor;
        this.courseArea = courseArea;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
    }
}
