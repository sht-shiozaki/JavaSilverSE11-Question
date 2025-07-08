package com.example.JavaSilverSE11_Question.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserAnswerId implements Serializable {

    private String userId;
    private String questionId;

    public UserAnswerId() {
    }

    public UserAnswerId(String userId, String questionId) {
        this.userId = userId;
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserAnswerId))
            return false;
        UserAnswerId that = (UserAnswerId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, questionId);
    }

    // getter, setter（必要なら）

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}