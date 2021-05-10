package com.example.admin.workorderlandlord.Model.Login;

public class ModelForUpdateIssue {


    private int id;
    private String workOrderNumber;
    private String dateEntered;
    private Object dateUpdated;
    private Object expectedStartDate;
    private Object expectedEndDate;
    private int processType;
    private int entityStatus;
    private String description;
    private int priorityType;
    private Object createdBy;
    private Object parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Object getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Object dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Object getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(Object expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public Object getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Object expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }

    public int getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(int entityStatus) {
        this.entityStatus = entityStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(int priorityType) {
        this.priorityType = priorityType;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }
}
