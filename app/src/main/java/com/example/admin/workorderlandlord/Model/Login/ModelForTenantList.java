package com.example.admin.workorderlandlord.Model.Login;

public class ModelForTenantList {

    /**
     * id : 43
     * workOrderNumber : WO-000043
     * dateEntered : 2018-10-03T00:00:00
     * dateUpdated : 2018-10-03T08:45:36.797
     * expectedStartDate : 2018-10-05T00:00:00
     * expectedEndDate : 2018-11-30T00:00:00
     * entityStatus : Close
     * description : Raise Issue is Closed Now.
     * priorityType : Medium
     */

    private int id;
    private String workOrderNumber;
    private String dateEntered;
    private String dateUpdated;
    private String expectedStartDate;
    private String expectedEndDate;
    private String entityStatus;
    private String description;
    private String priorityType;

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

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }
}
