package com.example.student;

public class Student {
    private final Integer studentId;
    private String StudentName;

    public Student(Integer studentId, String studentName) {
        this.studentId = studentId;
        StudentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return StudentName;
    }
}
