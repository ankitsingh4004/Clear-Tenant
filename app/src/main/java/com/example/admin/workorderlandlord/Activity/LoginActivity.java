package com.example.admin.workorderlandlord.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.workorderlandlord.Model.Login.LoginRequestModel;
import com.example.admin.workorderlandlord.Model.Login.LoginResponseModel;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.ApiServicesInterface;
import com.example.admin.workorderlandlord.remote.Network;
import com.example.admin.workorderlandlord.remote.PreferenceManager;
import com.example.admin.workorderlandlord.remote.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText user,password;
    Button signIn;
    String username1,userrole,userid;
    PreferenceManager preferenceManager;
    ProgressDialog progressDialog;
    TextView sms,TvRegistration;
    TextView forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TvRegistration = (TextView)findViewById(R.id.tvRegistration);
        TvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Registration.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
        preferenceManager= PreferenceManager.getInstance(getApplicationContext());
        user= (EditText) findViewById(R.id.user_id);
        user.setText("");
        password=(EditText) findViewById(R.id.user_password);
        password.setText("");
        signIn= (Button)findViewById(R.id.signIn);
        sms=(TextView) findViewById(R.id.DisPlay);
        forgotpassword = findViewById(R.id.forgotpassword);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLogin();
            }
        });



        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });

    }

    public void callLogin()
    {
        if (user.getText().toString().matches("")) {
            Toast.makeText(LoginActivity.this,"Please Enter Correct ID!",Toast.LENGTH_LONG).show();
        }else if(password.getText().toString().matches("")){
            Toast.makeText(LoginActivity.this,"Please Enter Correct Password!",Toast.LENGTH_LONG).show();
        }else {
            progressDialog.setMessage("Authenticating...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
            LoginRequestModel loginRequestModel=new LoginRequestModel();
            String musername = user.getText().toString().trim();
            String mpassword = password.getText().toString().trim();
            loginRequestModel.setUserName(musername);
            loginRequestModel.setPassword(mpassword);
            if (Utility.isNetworkAvailable(this)) {
                ApiServicesInterface apiServices = Network.getInstance().getApiServices();
                final Call<LoginResponseModel> loginResponseCall = apiServices.postLogin("application/json",loginRequestModel);
                loginResponseCall.enqueue(new Callback<LoginResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                        try {
                            int codeStatus=response.code();
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
                                if (response.body()!=null) {
                                   // Log.v("Response", response.body().toString());


                                    preferenceManager.setKey_User_Name(String.valueOf(response.body().username));
                                    preferenceManager.setKey_User_Id(String.valueOf(response.body().userid));
                                    preferenceManager.setKey_Person_Company_Id(String.valueOf(response.body().personcompanyid));
                                    preferenceManager.setKey_Parent_Company_Id(String.valueOf(response.body().parentcompanyid));
                                    username1 = response.body().getUsername();
                                    userid = String.valueOf(response.body().getUserid());
                                    userrole = response.body().getUserrole();

                                    Log.e("username",username1);





                                    if (userrole.trim().equals("Tenant")) {

                                        preferenceManager.setKey_User_Name(String.valueOf(response.body().username));

                                        String aa = preferenceManager.getKey_Person_Company_Id();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("Id", preferenceManager.getKey_Person_Company_Id());
                                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                                        intent.putExtra("Id", aa);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }

                                    else {
                                        progressDialog.dismiss();
                                        //sms.setText("Invalid Username or Password");
                                        //sms.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), "Invalid Username or Password.", Toast.LENGTH_LONG).show();

                                    }

                                }

                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Unauthorized Access.", Toast.LENGTH_LONG).show();
                           // Log.v("Error",e.getMessage().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                          progressDialog.dismiss();
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                       // System.out.println(t.getMessage() + t.getLocalizedMessage());
                       // sms.setText(t.getLocalizedMessage());
                       // sms.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                   // sms.setText("Network is not available");
                //sms.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_LONG).show();
            }
        }
    }
}
