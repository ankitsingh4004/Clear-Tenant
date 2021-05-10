package com.example.admin.workorderlandlord.Adapt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.workorderlandlord.Activity.UpdateIssue;
import com.example.admin.workorderlandlord.Model.Login.ModelForTenantList;
import com.example.admin.workorderlandlord.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterForTenant extends RecyclerView.Adapter<AdapterForTenant.MyViewHolder> {

    Context mContext;
    ClickListener mClickListner;
    List<ModelForTenantList> modelForTenantLists;


    Calendar calendar = Calendar.getInstance();
    String myFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    public AdapterForTenant(Context mContext, List<ModelForTenantList> modelForTenantLists) {
        this.mContext = mContext;
        this.modelForTenantLists = modelForTenantLists;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listwo, viewGroup, false);

        return new AdapterForTenant.MyViewHolder(mContext,itemView, viewType,mClickListner);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {



        if (modelForTenantLists.size()>0){
            final ModelForTenantList item = modelForTenantLists.get(position);
            String Id=String.valueOf(item.getId());
            holder.Unique.setText(Id);
            holder.dateExpected.setText(updateLabel(String.valueOf(item.getExpectedStartDate())));
            holder.woNumber.setText(String.valueOf(item.getWorkOrderNumber()));
            holder.dateUpdae.setText(updateLabel(String.valueOf(item.getDateUpdated())));
            holder.EndDate.setText(updateLabel(String.valueOf(item.getExpectedEndDate())));

            holder.dateEnter.setText(updateLabel(String.valueOf(item.getDateEntered())));
            holder.statusTV.setText(String.valueOf(item.getEntityStatus()));
            holder.UpdateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String WorkOrderId=String.valueOf(item.getId());
                    Bundle bundle=new Bundle();
                    bundle.putString("workOrderId",WorkOrderId);
                    Intent intent=new Intent(v.getContext(),UpdateIssue.class);
                    intent.putExtras(bundle);
//
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelForTenantLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Unique,woNumber,statusTV,dateEnter,dateUpdae,dateExpected,EndDate;
        ImageView UpdateImage;
        Context context;


        public  MyViewHolder(Context context, View itemView, Integer viewType, ClickListener mClickListner) {
            super(itemView);
            Unique = itemView.findViewById(R.id.tvUnique);
            woNumber = itemView.findViewById(R.id.tvwoNumber);
            dateEnter = itemView.findViewById(R.id.tvdateEnter);
            dateUpdae = itemView.findViewById(R.id.tvdateUpdae);
            dateExpected = itemView.findViewById(R.id.tvdateExpected);
            EndDate = itemView.findViewById(R.id.tvEndDate);
            statusTV= itemView.findViewById(R.id.tvstatus);
            UpdateImage = itemView.findViewById(R.id.UpdateImage);

        }
    }

    private String updateLabel(String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
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

}
