package org.mua.dto;

import org.mua.model.Application;
import org.mua.model.ReviewResult;

public class ApplicationReviewOverviewDTO {

    private Long applicationId;
    private Long userId;
    private String userName;
    private Long collegeId;
    private Long majorId;
    private String status;
    private Double score;  // 可为 null，表示未评分
    private ReviewResult.Grade grade;  // 可为 null，表示未评级

    // 构造函数，匹配查询返回的类型
    public ApplicationReviewOverviewDTO(Application application, Double score, ReviewResult.Grade grade) {
        this.applicationId = application.getId();
        this.userId = application.getUser().getId();
        this.userName = application.getUser().getUsername();
        this.collegeId = application.getCollegeId();
        this.majorId = application.getMajorId();
        this.status = application.getStatus().toString();
        this.score = score;
        this.grade = grade;
    }

    // Getters and Setters
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public ReviewResult.Grade getGrade() {
        return grade;
    }

    public void setGrade(ReviewResult.Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ApplicationReviewOverviewDTO{" +
                "applicationId=" + applicationId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", collegeId=" + collegeId +
                ", majorId=" + majorId +
                ", status='" + status + '\'' +
                ", score=" + score +
                ", grade=" + grade +
                '}';
    }
}
