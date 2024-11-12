// src/main/java/org/mua/model/EnrolledStudent.java
package org.mua.model;

import jakarta.persistence.*;

@Entity
@Table(name = "enrolled_students")
public class EnrolledStudent {

    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;  // 学籍号

    @Column(nullable = false)
    private String username;  // 姓名

    @Column(nullable = false)
    private String className;  // 班级

    @Column(name = "contact_info")
    private String contactInfo;  // 联系方式

    @Column(nullable = false, unique = true)
    private String idNumber;  // 身份证号

    @Column(nullable = false)
    private int gender;  // 性别：1为男，2为女

    public EnrolledStudent() {}

    // Getters and setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }
}
