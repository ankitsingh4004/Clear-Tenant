package com.example.admin.workorderlandlord.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.workorderlandlord.Model.Login.RegistrationRequestModel;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.ApiServicesInterface;
import com.example.admin.workorderlandlord.remote.Network;
import com.example.admin.workorderlandlord.remote.Utility;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    Button Submit;
    EditText Email,Fname,Lname,Vemail,Password,Pcheck,Address1,Address2,City,State,PostCode;
    String firstName,lastName,eMailId,vEmail,passWord,confirmPassWord,address1,address2,city,
    state,postalCode;
    Boolean temp = true;
    private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tenant Registration");
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        /*getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        Email  = (EditText)findViewById(R.id.etEmail);
        Fname  = (EditText)findViewById(R.id.etFname);
        Lname  = (EditText)findViewById(R.id.etLname);
        Vemail = (EditText)findViewById(R.id.etVemail);
        Submit = (Button) findViewById(R.id.btnSubmit);
        City   = (EditText)findViewById(R.id.etCity);
        Pcheck = (EditText)findViewById(R.id.etPcheck);
        State  = (EditText)findViewById(R.id.etState);
        Password = (EditText)findViewById(R.id.etPassword);
        Address1 = (EditText)findViewById(R.id.etAddress1);
        Address2 = (EditText)findViewById(R.id.etAddress2);
        PostCode = (EditText)findViewById(R.id.etPostCode);

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                validateEmail();
            }
        });

        Vemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                varify();
            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                password();

            }
        });

        Pcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                passwordMatch();

            }
        });



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationForm();
            }
        });

    }

    public void RegistrationForm(){

        if(Fname.getText().toString().trim().isEmpty()){
            Fname.setError("Please Enter First Name");
            Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_LONG).show();
            /*requestFocus();*/

        }else if(Lname.getText().toString().trim().isEmpty()){
            Lname.setError("Please Enter Last Name");
            Toast.makeText(this, "Please Enter Last Name", Toast.LENGTH_LONG).show();
            /*requestFocus();*/

        }else if (Email.getText().toString().isEmpty()){
            Email.setError("Please Enter your Email ID!!!");

        }else if (Vemail.getText().toString().trim().isEmpty()){
            Vemail.setError("Please Enter your Varify Email ID!!!");

        }else if(Password.getText().toString().isEmpty()){
            Password.setError("Password can't be blank!!!");

        }else if(Pcheck.getText().toString().trim().isEmpty()){
            Pcheck.setError("Please Enter Confirm Password");

        }else if (Address1.getText().toString().trim().isEmpty()){
            Address1.setError("Please Enter Address1!!!");
            Toast.makeText(this, "Please Enter Address1!!!", Toast.LENGTH_LONG).show();

        }/*else if (Address2.getText().toString().trim().isEmpty()){
            Address2.setError("Please Enter Address2!!!");
            Toast.makeText(this, "Please Enter Address2!!!", Toast.LENGTH_LONG).show();

        }*/else if (City.getText().toString().trim().isEmpty()){
            City.setError("Please Enter City!!!");
            Toast.makeText(this, "Please Enter City!!!", Toast.LENGTH_LONG).show();

        }else if (State.getText().toString().trim().isEmpty()){
            State.setError("Please Enter State!!!");
            Toast.makeText(this, "Please Enter State!!!", Toast.LENGTH_LONG).show();

        }else if (PostCode.getText().toString().trim().isEmpty()){
            PostCode.setError("Please Enter Post Code!!!");
            Toast.makeText(this, "Please Enter Post Code!!!", Toast.LENGTH_LONG).show();
        }
        else {

            firstName = Fname.getText().toString().trim();
            lastName  = Lname.getText().toString().trim();
            eMailId   = Email.getText().toString().trim();
            vEmail    = Vemail.getText().toString().trim();
            passWord  = Password.getText().toString().trim();
            confirmPassWord = Pcheck.getText().toString().trim();
            address1  = Address1.getText().toString().trim();
            address2  = Address2.getText().toString().trim();
            city      = City.getText().toString().trim();
            state     = State.getText().toString().trim();
            postalCode= PostCode.getText().toString().trim();
            if (!passWord.equals(confirmPassWord)){
                Toast.makeText(Registration.this,"Password Not matching",Toast.LENGTH_SHORT).show();
            }


            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setFirstName(firstName);
            registrationRequestModel.setLastName(lastName);
            registrationRequestModel.setEmailId(eMailId);
            registrationRequestModel.setVerifyEmailId(vEmail);
            registrationRequestModel.setPassword(passWord);
            registrationRequestModel.setPWCheck(confirmPassWord);
            registrationRequestModel.setBusinessAddress1(address1);
            registrationRequestModel.setBusinessAddress2(address2);
            registrationRequestModel.setBusinessCity(city);
            registrationRequestModel.setBusinessState(state);
            registrationRequestModel.setBusinessPostCode(postalCode);
            registrationRequestModel.setRoleId(10);
            registrationRequestModel.setCompanyType(7);


            if (Utility.isNetworkAvailable(getApplicationContext())){
                ApiServicesInterface apiServicesInterface = Network.getInstance().getApiServices();
                final Call<String> loginResponseCall = apiServicesInterface.
                        RegistrationRequest("application/json",registrationRequestModel);
                loginResponseCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try{
                            String Regi = response.body();
                            Toast.makeText(Registration.this,""+Regi,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Registration.this,LoginActivity.class);
                            startActivity(intent);
                        }catch (Exception ex){
                            Log.v("Error",ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        t.printStackTrace();
                        System.out.println(t.getMessage() + t.getLocalizedMessage());
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(),
                        "Network is not available", Toast.LENGTH_LONG).show();
            }

        }
    }




    private boolean validateEmail(){
        eMailId = Email.getText().toString().trim();
        if (eMailId.isEmpty()){
            Email.setError("Field Can't be empty!!!");
            return false;
        }else if (!EMAIL_ADDRESS_PATTERN.matcher(eMailId).matches()){
            Email.setError("Enter Valid Email ID!!!");
            return false;
        }else {
            Email.setError(null);
            return true;
        }
    }

    private boolean varify(){

        vEmail = Vemail.getText().toString().trim();
        eMailId = Email.getText().toString().trim();

        if (vEmail.isEmpty()){
            Vemail.setError("Field Can't be empty!!!");
            return false;

        }else if (!vEmail.equals(eMailId)){
                Vemail.setError("Varify Email does Not match!!!");
            return false;

        }else {
            Vemail.setError(null);
            return true;
        }

    }
    private boolean password(){

        passWord = Password.getText().toString().trim();

        if (passWord.isEmpty()){
            Password.setError("Field Can't be empty!!!");
            return false;
        }else {
            Email.setError(null);
            return true;
        }

    }

    private boolean passwordMatch(){
        passWord = Password.getText().toString().trim();
        confirmPassWord = Pcheck.getText().toString().trim();

        if (confirmPassWord.isEmpty()){
            Pcheck.setError("Field Can't be empty!!!");
            return false;

        }else if (!confirmPassWord.equals(passWord)){
            Pcheck.setError("Confirm Password does not match!!!");
            return false;

        }else {
            Pcheck.setError(null);
            return true;
        }
    }


}
