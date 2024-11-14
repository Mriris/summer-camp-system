package org.mua.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // 关联 users 表
    private User user;

    @Column(name = "application_date", nullable = false, updatable = false)
    private LocalDateTime applicationDate = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.UNPAID;

    @Column(name = "college_id", nullable = false)
    private Long collegeId;

    @Column(name = "major_id", nullable = false)
    private Long majorId;

    @Column(name = "advisor_id")
    private Long advisorId;

    // 新增字段
    @Column(name = "undergraduate_rank")
    private Integer undergraduateRank;

    @Column(name = "total_undergraduate_students")
    private Integer totalUndergraduateStudents;

    @Column(name = "awards", length = 500) // 将奖项字段设置为适当长度
    private String awards;

    @Column(name = "proof_pdf")
    private String proofPdf;

    // 枚举类型，用于报名状态
    public enum Status {
        UNPAID, PENDING, APPROVED, REJECTED
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Long advisorId) {
        this.advisorId = advisorId;
    }

    public Integer getUndergraduateRank() {
        return undergraduateRank;
    }

    public void setUndergraduateRank(Integer undergraduateRank) {
        this.undergraduateRank = undergraduateRank;
    }

    public Integer getTotalUndergraduateStudents() {
        return totalUndergraduateStudents;
    }

    public void setTotalUndergraduateStudents(Integer totalUndergraduateStudents) {
        this.totalUndergraduateStudents = totalUndergraduateStudents;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getProofPdf() {
        return proofPdf;
    }

    public void setProofPdf(String proofPdf) {
        this.proofPdf = proofPdf;
    }
}
