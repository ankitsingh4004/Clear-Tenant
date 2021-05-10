package com.example.admin.workorderlandlord.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {




    PreferenceManager preferenceManager;

    EditText newpass;
    EditText confirmpass;
    Button submit;
    String  username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newpass=findViewById(R.id.editTextnewPass);
        confirmpass=findViewById(R.id.editTextConfirmPass);
        submit=findViewById(R.id.submit);
        preferenceManager = PreferenceManager.getInstance(ChangePassword.this);

        username = preferenceManager.getKey_User_Name().trim();


        Log.d("username_new", username);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

                    RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
                    String get_url = "http://109.228.49.117:8096/api/account/ChangePasswordByLogin"+"?UserName="+preferenceManager.getKey_User_Name().trim()+"&NewPassword="+newpass.getText().toString().trim()+"&ReTypeNewPassword="+confirmpass.getText().toString().trim();

                    String post_url = "http://109.228.49.117:8096/api/account/ChangePasswordByLogin";

                    Log.v("confirmpassurl",get_url);
                    final ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();
                    try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UerName", username);
                    jsonObject.put("NewPassword", newpass.getText().toString().trim());
                    jsonObject.put("ReTypeNewPassword", confirmpass.getText().toString().trim());

                    Log.v("json",jsonObject.toString());



                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, get_url, null, new Response.Listener<JSONObject>() {
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
                                    Toast.makeText(ChangePassword.this,msg,Toast.LENGTH_SHORT).show();

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

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

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
               onBackPressed();

            }
        });


    }

}