package com.example.admin.workorderlandlord.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.workorderlandlord.Adapt.MessageListAdapter;
import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.PreferenceManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageList extends AppCompatActivity {

    RecyclerView recyclerView;
    JSONObject jsonObject=null;
    JSONArray jsonArray=null;
    public ProgressDialog dialog;
    PreferenceManager preferenceManager;
    SwipeRefreshLayout refreshLayout;
    int PersonCompanyID,ParentComapanyId;
    String RoleName;
    ArrayList<String> WorkorderNumberList,IDList,MessageTextList,DateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);


        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Messages");

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });

        preferenceManager= PreferenceManager.getInstance(getApplicationContext());
        PersonCompanyID=Integer.valueOf(preferenceManager.getKey_Person_Company_Id());
        ParentComapanyId=Integer.valueOf(preferenceManager.getKey_Parent_Company_Id());
        RoleName=preferenceManager.getKey_User_Role();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.recycler_messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        WorkorderNumberList= new ArrayList<>();
        IDList= new ArrayList<>();
        MessageTextList= new ArrayList<>();
        DateList = new ArrayList<>();

        WorkorderNumberList.clear();
        IDList.clear();
        MessageTextList.clear();
        DateList.clear();

        GetMessageList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    void GetMessageList() {

        jsonObject = new JSONObject();

        dialog = ProgressDialog.show(MessageList.this, "Fetching...",
                "Please wait...", true);
        final String url = "http://109.228.49.117:8096/api/workorderfortenant/getallMassageforTenant?CompanyId="+PersonCompanyID+"&RoleName=Tenant";
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
              //  Gson gson = new Gson();
                Log.d("messageListurl",url);


                Log.d("messageListurlresponse",s);


                try{
                    jsonArray=new JSONArray(s);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (jsonArray.length()>0){

                    for (int i=0;jsonArray.length()>i;i++){
                        try {
                            jsonObject=jsonArray.getJSONObject(i);

                            WorkorderNumberList.add(jsonObject.getString("workOrderNumber"));
                            IDList.add(jsonObject.getString("id"));
                            MessageTextList.add(jsonObject.getString("messageText"));

                            String date=jsonObject.getString("dateOfMessage");
                            date=date.substring(0,date.indexOf("T"));
                            DateList.add(date);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    MessageListAdapter adapter=new MessageListAdapter(MessageList.this,WorkorderNumberList,
                            IDList,MessageTextList,DateList);
                            recyclerView.setAdapter(adapter);
                }

                dialog.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                String error = volleyError.getMessage();
                Toast.makeText(MessageList.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue rQueue = Volley.newRequestQueue(MessageList.this);
                rQueue.add(request);
    }

}
