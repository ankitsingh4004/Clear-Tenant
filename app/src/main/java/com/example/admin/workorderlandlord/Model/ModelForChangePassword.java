package com.example.admin.workorderlandlord.Model;

public class ModelForChangePassword {


    private boolean success;
    private String savemgs;
    private String otp;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSavemgs() {
        return savemgs;
    }

    public void setSavemgs(String savemgs) {
        this.savemgs = savemgs;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
