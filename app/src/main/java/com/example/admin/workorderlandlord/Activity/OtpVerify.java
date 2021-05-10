package com.example.admin.workorderlandlord.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.workorderlandlord.R;

public class OtpVerify extends AppCompatActivity {

    String email;
    String code1;
    EditText otp;
    Button submit;
    String otptext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        otp=findViewById(R.id.otp);
        submit=findViewById(R.id.submit);

        email=getIntent().getStringExtra("email");
       // code1=getIntent().getStringExtra("code");
        otptext=getIntent().getStringExtra("otp");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp.getText().toString().equals(otptext)) {
                    Intent i=new Intent(OtpVerify.this,ResetPassword.class);
                    i.putExtra("email",email);
                    i.putExtra("code",otptext);
                    startActivity(i);
                } else {
                    Toast.makeText(OtpVerify.this,"Required Otp is wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
