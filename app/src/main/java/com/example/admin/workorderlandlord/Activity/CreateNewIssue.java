package com.example.admin.workorderlandlord.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.workorderlandlord.Model.Login.CreateIssueRequestModel;
import com.example.admin.workorderlandlord.Model.Login.ModelForPriority;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.ApiServicesInterface;
import com.example.admin.workorderlandlord.remote.Network;
import com.example.admin.workorderlandlord.remote.PreferenceManager;
import com.example.admin.workorderlandlord.remote.Utility;
import com.google.android.material.textfield.TextInputEditText;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewIssue extends AppCompatActivity {
    EditText WoNumber,DateEnter,Description;


    Spinner Priority;
    Button Create,back;
    List<ModelForPriority> modelForPriorities;
    String priorityPostion,woNumber;
    int priority;
    String workOrderNumber,dateEnter,description,WOpriority;
    Calendar calendar = Calendar.getInstance();
    JSONObject jsonObject=null;
    JSONArray jsonArray=null;
    JSONObject jsonObject1=null;
    PreferenceManager preferenceManager;

    int PersonCompanyID,ParentComapanyId;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_issue);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Issue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferenceManager= PreferenceManager.getInstance(getApplicationContext());

        PersonCompanyID=Integer.parseInt(preferenceManager.getKey_Person_Company_Id());
        ParentComapanyId=Integer.parseInt(preferenceManager.getKey_Parent_Company_Id());

        WoNumber = (EditText)findViewById(R.id.tvWoNumber);
        DateEnter = (EditText)findViewById(R.id.tvDateEnter);
        Description = (EditText)findViewById(R.id.tvDescription);
        Priority = (Spinner)findViewById(R.id.SpinnerPriority);
        Create = (Button)findViewById(R.id.btnCreate);
        back = (Button)findViewById(R.id.btnback);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        WoNumber.setEnabled(false);
        DateEnter.setEnabled(false);
        Description.setFocusable(true);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateNewIssue.this,NavigationActivity.class);
                startActivity(intent);

            }
        });





        Priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name= String.valueOf(Priority.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
         DatePickerDialog datePickerDialog =
                 new DatePickerDialog(CreateNewIssue.this,date,calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                String showDate = DateFormat.getDateInstance().format(new Date());
                DateEnter.setText(showDate);
               /* datePickerDialog.show();*/
        SpinnerForPriority();

        GetWorkOrder();


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        DateEnter.setText(sdf.format(calendar.getTime()));
    }


    private void SpinnerForPriority(){

        if (Utility.isNetworkAvailable(getApplicationContext())){

            ApiServicesInterface apiServices = Network.getInstance().getApiServices();
            final Call<List<ModelForPriority>> ListCall = apiServices.PriorityForCreation
                    ("application/json","api/workorderfortenant/getpriorities");
            ListCall.enqueue(new Callback<List<ModelForPriority>>() {
                @Override
                public void onResponse(Call<List<ModelForPriority>> call, Response<List<ModelForPriority>> response) {

                    if (response.isSuccessful()){

                        modelForPriorities = response.body();
                        if (getApplicationContext() !=null){
                            String[] item = new String[modelForPriorities.size()];
                            for (int i =0;i<modelForPriorities.size();i++){
                                item[i] = modelForPriorities.get(i).getText();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                                    R.layout.fortitle,item);
                            adapter.setDropDownViewResource(R.layout.fortitle);
                            Priority.setAdapter(adapter);
                            Priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    priority =  Integer.parseInt(modelForPriorities.get(position).getValue());
                                    priorityPostion= String.valueOf(position);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }

                    }

                }

                @Override
                public void onFailure(Call<List<ModelForPriority>> call, Throwable t) {
                    t.printStackTrace();
                    System.out.println(t.getMessage() + t.getLocalizedMessage());

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_SHORT).show();
        }
    }

    void GetWorkOrder() {

        jsonObject = new JSONObject();

        String url = "http://109.228.49.117:8096/api/workorderfortenant/getworkordernumber";
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                try{
                    woNumber=(String)s;
                    woNumber.replace("\"","");
                    woNumber=woNumber.substring(1,woNumber.length()-1);
                    WoNumber.setText(woNumber);
                }catch (Exception e){
                    e.printStackTrace();
                }
                SpinnerForPriority();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String error = volleyError.getMessage();
                SpinnerForPriority();
                Toast.makeText(CreateNewIssue.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rQueue = Volley.newRequestQueue(CreateNewIssue.this);
        rQueue.add(request);
    }




    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void Validation(){
        if (Description.getText().toString().isEmpty()){
            Description.setError("Please Enter Description");
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_LONG).show();
        }else if (Priority.getSelectedItem().toString().trim().equals("Select Priority")){
            Toast.makeText(this, "Please Select Priority", Toast.LENGTH_LONG).show();
        }
        else {
            CreatIssueRequest();
        }

    }

    public void CreatIssueRequest(){

        workOrderNumber = WoNumber.getText().toString().trim();
        description = Description.getText().toString().trim();


        CreateIssueRequestModel createIssue = new CreateIssueRequestModel();
        createIssue.setDescription(description);
        createIssue.setWorkOrderNumber(woNumber);
        createIssue.setPriorityType(priority);
        createIssue.setCreatedBy(PersonCompanyID);
        createIssue.setParentId(ParentComapanyId);

        if (Utility.isNetworkAvailable(getApplicationContext())){
            ApiServicesInterface apiServicesInterface = Network.getInstance().getApiServices();
            final Call<String> CreateIssueCall = apiServicesInterface.
                    CreateIssueRequest("application/json",createIssue);
            CreateIssueCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.v("CreateRequest",call.request().toString());


                    try{
                        String CreateIssueRespons = response.body();

                        Toast.makeText(CreateNewIssue.this,""+CreateIssueRespons,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreateNewIssue.this,NavigationActivity.class);
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
