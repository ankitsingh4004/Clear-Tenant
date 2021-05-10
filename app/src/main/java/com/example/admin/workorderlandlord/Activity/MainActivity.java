package com.example.admin.workorderlandlord.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.workorderlandlord.Adapt.AdapterForTenant;
import com.example.admin.workorderlandlord.Model.Login.ModelForTenantList;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.ApiServicesInterface;
import com.example.admin.workorderlandlord.remote.Network;
import com.example.admin.workorderlandlord.remote.PreferenceManager;
import com.example.admin.workorderlandlord.remote.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelForTenantList>modelForTenantLists;
    AdapterForTenant adapter;
    String ID="";
    TextView tvRecordFound;
    SwipeRefreshLayout refreshLayout;
    Button MessageBtn,CreateIssueBtn,LogoutBtn;
    PreferenceManager preferenceManager;

    int PersonCompanyID,ParentComapanyId;
    String RoleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        onRefresh();
                    }
                },4000);
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.tenantRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        preferenceManager= PreferenceManager.getInstance(getApplicationContext());

        PersonCompanyID=Integer.valueOf(preferenceManager.getKey_Person_Company_Id());
        ParentComapanyId=Integer.valueOf(preferenceManager.getKey_Parent_Company_Id());
        RoleName=preferenceManager.getKey_User_Role();
        MessageBtn=(Button) findViewById(R.id.messageBtn);
        CreateIssueBtn=(Button) findViewById(R.id.createIssueBtn);
        LogoutBtn=(Button) findViewById(R.id.LogoutBtn);


        MessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,MessageList.class);
                startActivity(intent);
            }
        });

        CreateIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainActivity.this,CreateNewIssue.class);
                startActivity(intent2);

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* editor.remove(preferenceManager.getKey_User_Name());
                editor.remove(preferenceManager.getKey_User_Id());*/


                showDialog();




            }
        });

        Intent intent=getIntent();
        ID=intent.getStringExtra("Id");

        TenantList();


    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.inflate_logout_dialogue);
        TextView tv_no = dialog.findViewById(R.id.tv_dialog_check_no);
        TextView tv_yes = dialog.findViewById(R.id.tv_dialog_check_yes);

        dialog.setTitle("Alert");
        dialog.setCancelable(false);
        dialog.show();


        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this,LoginActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();

            }
        });


    }

    public void TenantList(){

        if (Utility.isNetworkAvailable(getApplicationContext())){

            ApiServicesInterface apiServices = Network.getInstance().getApiServices();
            final Call<List<ModelForTenantList>> ListCall = apiServices.TenantList
                    ("application/json","api/workorderfortenant/issuelistfortenent?rolename=Tenant&companyid="+PersonCompanyID);
            ListCall.enqueue(new Callback<List<ModelForTenantList>>() {
                @Override
                public void onResponse(Call<List<ModelForTenantList>> call, Response<List<ModelForTenantList>> response) {

                    if (response.isSuccessful()){

                        modelForTenantLists = response.body();
                        if (modelForTenantLists.size() == 0){
                            tvRecordFound = (TextView)findViewById(R.id.tvRecordFound);
                            tvRecordFound.setText("No Record Found!!!");

                        }
                        calTenantResponse(modelForTenantLists);

                    }

                }

                @Override
                public void onFailure(Call<List<ModelForTenantList>> call, Throwable t) {
                    t.printStackTrace();
                    System.out.println(t.getMessage() + t.getLocalizedMessage());

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void calTenantResponse(List<ModelForTenantList>TenantLists){


        adapter = new AdapterForTenant(this,TenantLists);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
       Intent intent = new Intent(MainActivity.this,LoginActivity.class);
       startActivity(intent);
       finish();
    }

}
