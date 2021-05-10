package com.example.admin.workorderlandlord.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.workorderlandlord.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPassword extends AppCompatActivity {
    EditText newpass;
    EditText confirmpass;
    EditText code;
    Button submit;
    String email;
    String code1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newpass=findViewById(R.id.newpass);
        confirmpass=findViewById(R.id.confirmpass);
        code=findViewById(R.id.code);
        submit=findViewById(R.id.submit);

        email=getIntent().getStringExtra("email");
        code1=getIntent().getStringExtra("code");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasNonAlpha = newpass.getText().toString().matches("^.*[^a-zA-Z0-9 ].*$");
                if (newpass.getText().toString().equals("")) {
                    newpass.requestFocus();
                    newpass.setError("Please enter new password");
                }else if (confirmpass.getText().toString().equals("")) {
                    confirmpass.requestFocus();
                    confirmpass.setError("Please enter confirm password");
                }else if (!confirmpass.getText().toString().equalsIgnoreCase(newpass.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Please enter new password and confirm password same.",Toast.LENGTH_LONG).show();
                }else if(!hasNonAlpha){
                    Toast.makeText(getApplicationContext(),"Passwords must have at least one non alphanumeric character.",Toast.LENGTH_LONG).show();
                } else {

                    RequestQueue requestQueue = Volley.newRequestQueue(ResetPassword.this);
                    String url = "http://109.228.49.117:8096/api/account/ChangePassword?UserName="+email.trim()+"&NewPassword="+newpass.getText().toString().trim()+"&ReTypeNewPassword="+confirmpass.getText().toString().trim()+"&otp="+code1;



                    Log.v("confirmpassurl",url);
                    final ProgressDialog progressDialog = new ProgressDialog(ResetPassword.this);
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                          //  VolleyLog.wtf(response.toString());
                            Log.d("confirmpassresp",response.toString());
                            progressDialog.dismiss();
                            try {
                                String msg= response.getString("savemgs");
                                if(response.getBoolean("success")){

                                    showDialog();
                                } else {
                                    Toast.makeText(ResetPassword.this,msg,Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }



                        }
                    },  new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e("TAG", error.getMessage(), error);
                        }


                    }){
                        @Override
                        public String getBodyContentType(){
                            return "application/json";
                        }
                    };



                    requestQueue.add(jsonObjectRequest);

/*
                    StringRequest sr = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();

                                    Log.e("HttpClient", "success! response: " + response.toString());

                                    progressDialog.dismiss();
                                    Log.e("HttpClient", "success! response: " + response.toString());



                                }


                            },
                           new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG", error.getMessage(), error);
                        }


                    }){
                        @Override
                        public String getBodyContentType(){
                            return "application/json";
                        }
                    };


                    requestQueue.add(sr);*/






                }
            }
        });
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.inflate_dialogue_link_send);
        TextView tv_ok = dialog.findViewById(R.id.tv_dialog_check_ok);
        dialog.setTitle("Alert");
        dialog.setCancelable(false);
        dialog.show();


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(ResetPassword.this,LoginActivity.class));

            }
        });


    }


}


