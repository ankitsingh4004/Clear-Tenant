package com.example.admin.workorderlandlord.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.workorderlandlord.R;


import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {

    EditText et_login_user_id;
    Button submit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        et_login_user_id=findViewById(R.id.et_login_user_id);
        submit=findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_login_user_id.getText().toString().equals("")) {
                    et_login_user_id.requestFocus();
                    et_login_user_id.setError("Please enter email address");
                } else {
                    // Log.v("response", UrlClass.Forgot_Password+et_login_user_id.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(ForgetPassword.this);
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();
                    RequestQueue queue = Volley.newRequestQueue(ForgetPassword.this);

                    RequestQueue requestQueue = Volley.newRequestQueue(ForgetPassword.this);
                    String url = "http://109.228.49.117:8096/api/account/IforgetPassword?UserName="+et_login_user_id.getText().toString();
                    JSONObject jsonBody = new JSONObject();

                    Log.d("emailreseturl",url);


                    try {
                        jsonBody.put("UserName", et_login_user_id.getText().toString());


                        final String requestBody = jsonBody.toString();
                        Log.d("request",requestBody);



                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                VolleyLog.wtf(response.toString());
                                progressDialog.dismiss();
                                Log.d("emailresetres",response.toString());
                                String otp = "";


                                try {

                                    if(response.getBoolean("success")){

                                        if(otp!=null){
                                            otp = response.getString("otp");
                                        } else
                                            Toast.makeText(ForgetPassword.this,"OTP is null",Toast.LENGTH_SHORT).show();



                                    }   } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent i=new Intent(ForgetPassword.this,OtpVerify.class);
                                i.putExtra("email",et_login_user_id.getText().toString());
                                // i.putExtra("code",response.getString("code"));
                                i.putExtra("otp",otp);
                                startActivity(i);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                VolleyLog.wtf(error.toString());
                            }




                        });





                        queue.add(jsonObjectRequest);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }}


        });
    }






}


