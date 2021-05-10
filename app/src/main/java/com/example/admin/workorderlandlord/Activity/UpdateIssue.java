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

import com.example.admin.workorderlandlord.Model.Login.ModelForPriority;
import com.example.admin.workorderlandlord.Model.Login.ModelForUpdateIssue;
import com.example.admin.workorderlandlord.Model.UpdateIssueModel;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.ApiServicesInterface;
import com.example.admin.workorderlandlord.remote.Network;
import com.example.admin.workorderlandlord.remote.PreferenceManager;
import com.example.admin.workorderlandlord.remote.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateIssue extends AppCompatActivity {

    EditText UpdateNumber,UpdateDateEnter,UpdateDescription,ExStartDate,ExEndDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat newformat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    Spinner UpdatePriority,UpdateStatus;
    Button BackToList,buttonupdateIssue;
    ModelForUpdateIssue modelForUpdateIssue;
    String updatePriority,WOid,Wonumber,updateEnter,description,exStarDate,exEndDate;
    List<ModelForPriority> modelForPriorities;
    Calendar calendar = Calendar.getInstance();
    private String Updatedexstartdate = "";
    private Calendar c1,c2;

    String valueExstartdate= "";
    String valueExEnddate= "";


    PreferenceManager preferenceManager;

    int PersonCompanyID,WorkOrderId,processType;

    private String Updatedexenddate = "";
    private int newpriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_issue);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Issue");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle intent=getIntent().getExtras();
        if(intent!=null){
            WOid = intent.getString("workOrderId");
        }
        UpdateNumber = (EditText) findViewById(R.id.tvUpdateNumber);
        UpdateDateEnter = (EditText) findViewById(R.id.tvUpdateDateEnter);
        UpdateDescription = (EditText) findViewById(R.id.tvUpdateDescription);
        ExStartDate = (EditText) findViewById(R.id.etExStartDate);
        ExEndDate = (EditText) findViewById(R.id.etExEndDate);
        UpdatePriority = (Spinner)findViewById(R.id.SpinnerUpdatePriority);
        BackToList = (Button)findViewById(R.id.btnbacktoList);
        buttonupdateIssue = findViewById(R.id.btnUpdateIssue);
        preferenceManager= PreferenceManager.getInstance(getApplicationContext());
        PersonCompanyID=Integer.parseInt(preferenceManager.getKey_Person_Company_Id());
        BackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpdateIssue.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                /*updateLabel();*/
            }
        };
       /* UpdateDateEnter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(UpdateIssue.this,date,calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });*/
        ExStartDate.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(UpdateIssue.this,date,calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
              datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                         c1 = Calendar.getInstance();
                        c1.set(Calendar.YEAR, i);
                        c1.set(Calendar.MONTH, i1);
                        c1.set(Calendar.DAY_OF_MONTH, i2);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                        SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");

                        String starttime = df.format(c1.getTime());
                        Updatedexstartdate = df4.format(c1.getTime());
                        String exstartdate1 = String.valueOf(c1.getTime());
                        Log.d("exstartdateset",Updatedexstartdate);
                        ExStartDate.setText(starttime);
                    }
                });
                datePickerDialog.show();

            }
        });
        ExEndDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(UpdateIssue.this,date,calendar.get(Calendar.YEAR),


                                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, i);
                        c.set(Calendar.MONTH, i1);
                        c.set(Calendar.DAY_OF_MONTH, i2);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                        SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");

                        String endtime = df.format(c.getTime());
                        Updatedexenddate = df4.format(c.getTime());
                        String exstartdate1 = String.valueOf(c.getTime());
                        Log.d("exenddateset",Updatedexenddate);
                        ExEndDate.setText(endtime);
                    }
                });

            }
        });

        buttonupdateIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });



        getIssueDetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void validation(){
        if (UpdateDescription.getText().toString().isEmpty()){
            UpdateDescription.setError("Please Enter Description");
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_LONG).show();
        }else if (UpdatePriority.getSelectedItem().toString().trim().equals("Select Priority")){
            Toast.makeText(this, "Please Select Priority", Toast.LENGTH_LONG).show();
        }else if (ExStartDate.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Select a date", Toast.LENGTH_LONG).show();
        }else if (ExEndDate.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Select a date", Toast.LENGTH_LONG).show();
        }
        else {
           funUpdateIssue();
        }

    }

    public void funUpdateIssue(){


        if(Updatedexenddate==null||Updatedexenddate==""){
            Updatedexenddate = valueExEnddate;
        }

        if(Updatedexstartdate==null||Updatedexstartdate==""){
            Updatedexstartdate = valueExstartdate;
        }


       int  workOrderNumber = WorkOrderId;
        description = UpdateDescription.getText().toString().trim();


        UpdateIssueModel updateIssue = new UpdateIssueModel();
       updateIssue.setDescription(description);
       updateIssue.setExpectedEndDate(Updatedexenddate);
       updateIssue.setId(workOrderNumber);
       updateIssue.setPriorityType(newpriority);
      updateIssue.setExpectedStartDate(Updatedexstartdate);
       updateIssue.setProcessType(processType);
        updateIssue.setCreatedBy(PersonCompanyID);

        if (Utility.isNetworkAvailable(getApplicationContext())){
            ApiServicesInterface apiServicesInterface = Network.getInstance().getApiServices();
            final Call<String> UpdateIssueCall = apiServicesInterface.
                    UpdateIssueRequest("application/json",updateIssue);
            UpdateIssueCall.enqueue(new Callback<String>() {



                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                    Log.v("UpdateIssueRequest",call.request().toString());

                    Log.v("UpdateIssueRequest2",UpdateIssueCall.request().toString());

                    try{
                        String UpdateIssueRespons = response.body();
                        Log.v("UpdateIssueResponse",UpdateIssueRespons);
                        Toast.makeText(UpdateIssue.this,""+UpdateIssueRespons,Toast.LENGTH_LONG).show();
                     /*   Intent intent = new Intent(UpdateIssue.this,NavigationActivity.class);
                        startActivity(intent);*/
                        onBackPressed();
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

    private String updateLabel(String dateFormat) {
       String newdateFormat;
        String[] dat = dateFormat.split("T");
        try {
            Date date22=simpleDateFormat.parse(dat[0]);
            newdateFormat=newformat.format(date22);
            return  newdateFormat;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    private void getIssueDetails(){

        if (Utility.isNetworkAvailable(getApplicationContext())){

            ApiServicesInterface apiServices = Network.getInstance().getApiServices();
            final Call<ModelForUpdateIssue> ListCall = apiServices.modelForUpdateIssue
                    ("application/json","api/workorderfortenant/Editissue?Id="+WOid);
            ListCall.enqueue(new Callback<ModelForUpdateIssue>() {
                @Override
                public void onResponse(Call<ModelForUpdateIssue> call, Response<ModelForUpdateIssue> response) {

                    if (response.isSuccessful()){

                        Log.d("getupdateResponse", response.toString());

                        modelForUpdateIssue = response.body();
                       Wonumber = modelForUpdateIssue.getWorkOrderNumber();
                        WorkOrderId = modelForUpdateIssue.getId();
                        processType = modelForUpdateIssue .getProcessType();
                        UpdateNumber.setText(Wonumber);
                       updateEnter =updateLabel( modelForUpdateIssue.getDateEntered());
                        UpdateDateEnter.setText(updateEnter);
                       description = modelForUpdateIssue.getDescription();
                        UpdateDescription.setText(description);

                        valueExstartdate= String.valueOf(modelForUpdateIssue.getExpectedStartDate());
                        valueExEnddate= String.valueOf(modelForUpdateIssue.getExpectedEndDate());

                       exStarDate =updateLabel(String.valueOf(modelForUpdateIssue.getExpectedStartDate()));
                        ExStartDate.setText(exStarDate);
                       exEndDate =updateLabel(String.valueOf(modelForUpdateIssue.getExpectedEndDate()));
                        ExEndDate.setText(exEndDate);
                        updatePriority = String.valueOf(modelForUpdateIssue.getPriorityType());
                        SpinnerForUpdatePriority();



                    }

                }

                @Override
                public void onFailure(Call<ModelForUpdateIssue> call, Throwable t) {
                    t.printStackTrace();
                    System.out.println(t.getMessage() + t.getLocalizedMessage());

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void SpinnerForUpdatePriority(){

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
                                if(modelForPriorities.get(i).getValue().equals(updatePriority)){

                                    showupdateIssue(modelForPriorities.get(i).getText());
                                }
                            }

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

    private void showupdateIssue(String compareValue)
    {
        if(getApplicationContext()!=null){

            String[] item =new String[modelForPriorities.size()];
            for(int i=0;i<modelForPriorities.size();i++)
            {
                item[i] = modelForPriorities.get(i).getText();
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.fortitle,item);
            adapter.setDropDownViewResource(R.layout.fortitle);
            UpdatePriority.setAdapter(adapter);
            if (compareValue != null) {
                int spinnerPosition = adapter.getPosition(compareValue);
                UpdatePriority.setSelection(spinnerPosition);
            }
            UpdatePriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    newpriority =  Integer.parseInt(modelForPriorities.get(position).getValue());


                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

}
