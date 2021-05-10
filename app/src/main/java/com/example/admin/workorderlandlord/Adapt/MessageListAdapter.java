package com.example.admin.workorderlandlord.Adapt;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.workorderlandlord.R;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    ArrayList<String> WorkorderNumberList,IDList,MessageTextList,DateList;
    Context mContext;

    public MessageListAdapter(Context context, ArrayList<String> WorkorderNumberList1, ArrayList<String> IDList1,
            ArrayList<String> MessageTextList1, ArrayList<String> DateList1) {

        this.mContext = context;
        WorkorderNumberList= new ArrayList<>();
        IDList= new ArrayList<>();
        MessageTextList= new ArrayList<>();
        DateList = new ArrayList<>();

        this.IDList = IDList1;
        this.MessageTextList=MessageTextList1;
        this.DateList=DateList1;
        this.WorkorderNumberList = WorkorderNumberList1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messagelist_single_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.WorkOrderNoTV.setText(WorkorderNumberList.get(position));
        holder.DateTv.setText(DateList.get(position));
        holder.MessageTv.setText(MessageTextList.get(position));

    }

    @Override
    public int getItemCount() {
        return IDList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView WorkOrderNoTV,DateTv,MessageTv;

        public MyViewHolder(View view) {
            super(view);
            WorkOrderNoTV = (TextView) view.findViewById(R.id.workOrderNumber_messageAdapter);
            DateTv = (TextView) view.findViewById(R.id.date_messageAdapter);
            MessageTv = (TextView) view.findViewById(R.id.message_messageAdapter);

        }
    }
}



