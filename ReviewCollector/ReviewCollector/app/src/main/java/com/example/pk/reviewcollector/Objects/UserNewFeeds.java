package com.example.pk.reviewcollector.Objects;

import java.io.Serializable;

/**
 * Created by jaani on 9/6/2017.
 */

public class UserNewFeeds implements Serializable {

    String Description,EndsIn,GroupId,Id,IsShared,QuestionId,StartedOn,Status,Title;
    String[][] Questions;

    public String[][] getQuestions() {
        return Questions;
    }

    public void setQuestions(String[][] questions) {
        Questions = questions;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEndsIn() {
        return EndsIn;
    }

    public void setEndsIn(String endsIn) {
        EndsIn = endsIn;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsShared() {
        return IsShared;
    }

    public void setIsShared(String isShared) {
        IsShared = isShared;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getStartedOn() {
        return StartedOn;
    }

    public void setStartedOn(String startedOn) {
        StartedOn = startedOn;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
