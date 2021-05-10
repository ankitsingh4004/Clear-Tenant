package com.example.admin.workorderlandlord.remote;

import com.example.admin.workorderlandlord.Model.Login.CreateIssueRequestModel;
import com.example.admin.workorderlandlord.Model.Login.LoginRequestModel;
import com.example.admin.workorderlandlord.Model.Login.LoginResponseModel;
import com.example.admin.workorderlandlord.Model.Login.ModelForGender;
import com.example.admin.workorderlandlord.Model.Login.ModelForPriority;
import com.example.admin.workorderlandlord.Model.Login.ModelForTenantList;
import com.example.admin.workorderlandlord.Model.Login.ModelForTitle;
import com.example.admin.workorderlandlord.Model.Login.ModelForUpdateIssue;
import com.example.admin.workorderlandlord.Model.Login.RegistrationRequestModel;
import com.example.admin.workorderlandlord.Model.ModelForChangePassword;
import com.example.admin.workorderlandlord.Model.UpdateIssueModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiServicesInterface {

    @POST("api/account/login")
    Call<LoginResponseModel> postLogin(
            @Header("Content-Type")
                    String headerValue,
            @Body LoginRequestModel loginRequestModel
    );

    @POST("api/workorderfortenant/createissue")
    Call<String> CreateIssueRequest(
            @Header("Content-Type")
                    String headerValue,
            @Body CreateIssueRequestModel createIssueRequestModel
    );

    @POST("api/workorderfortenant/Edit_issue")
    Call<String> UpdateIssueRequest(
            @Header("Content-Type")
                    String headerValue,
            @Body UpdateIssueModel updateIssueModel
    );


    @POST("api/account/registertenant")
    Call<String> RegistrationRequest(
            @Header("Content-Type")
                    String headerValue,
            @Body RegistrationRequestModel postResquest
    );

    @GET
    Call<List<ModelForTitle>> TitleForregistretion(
            @Header("Content-Type")
                    String headerValue,
            @Url String url
    );

    @GET
    Call<List<ModelForPriority>> PriorityForCreation(
            @Header("Content-Type")
                    String headerValue,
            @Url String url
    );

    @GET
    Call<List<ModelForGender>> GenderForregistretion(
            @Header("Content-Type")
                    String headerValue,
            @Url String url
    );

    @GET
    Call<ModelForUpdateIssue> modelForUpdateIssue(
            @Header("Content-Type")
                    String headerValue,
            @Url String url
    );

    @GET
    Call<List<ModelForTenantList>> TenantList(
            @Header("Content-Type")
                    String headerValue,
            @Url String url
    );

    @GET
    Call<List<ModelForChangePassword>> ResetPassword(
            @Header("Content-Type")
                    String headerValue,
            @Url String url
    );


}
