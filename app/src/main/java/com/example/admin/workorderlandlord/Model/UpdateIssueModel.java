package com.example.admin.workorderlandlord.Model;

public class UpdateIssueModel {

    private int id;
    private String Description;
    private String ExpectedEndDate;
    private int priorityType;
    private String ExpectedStartDate;
    private int ProcessType;
    private int createdBy;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getExpectedEndDate(String updatedexenddate) {
        return ExpectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        ExpectedEndDate = expectedEndDate;
    }

    public int getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(int priorityType) {
        this.priorityType = priorityType;
    }

    public String getExpectedStartDate() {
        return ExpectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        ExpectedStartDate = expectedStartDate;
    }

    public int getProcessType() {
        return ProcessType;
    }

    public void setProcessType(int processType) {
        ProcessType = processType;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
