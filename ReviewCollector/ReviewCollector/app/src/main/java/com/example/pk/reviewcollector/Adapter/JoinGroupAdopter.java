package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pk.reviewcollector.Create_Poll;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.ViewReportsByUserJoin;
import com.example.pk.reviewcollector.ViewReportsListActivity;

import java.util.List;

/**
 * Created by jaani on 9/7/2017.
 */


public class JoinGroupAdopter extends RecyclerView.Adapter<JoinGroupAdopter.MyViewHolder> {

    private List<Grouplist> dataList;
    Context context;
    Activity act;
    private Context mContext;
    private Activity mActivity;
    boolean click = true;

    private RelativeLayout mRelativeLayout;
    private Button mButton;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tittle,description,rules;

        public LinearLayout groupevent;

            public MyViewHolder(View view) {
                super(view);

                tittle = (TextView) view.findViewById(R.id.groupname);
                rules=(TextView) view.findViewById(R.id.rules);
                description = (TextView) view.findViewById(R.id.groupdescription);
                groupevent=(LinearLayout)view.findViewById(R.id.groupevent);

            }

        }



    public JoinGroupAdopter(List<Grouplist> moviesList, Context context,Activity a) {
        this.dataList = moviesList;
        this.context = context;
        this.act=a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joingrouplist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Grouplist groupdata = dataList.get(position);
        mContext = context;
        mActivity =act;

        // Get the widgets reference from XML layout
        holder.tittle.setText(groupdata.getTitle());
        holder.description.setText(groupdata.getDescription());
        holder.rules.setText(groupdata.getRules());

        holder.groupevent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent=new Intent(context, ViewReportsByUserJoin.class);
                intent.putExtra("gid", groupdata.getId());
                intent.putExtra("tittle", groupdata.getTitle());
                intent.putExtra("status",groupdata.getStatus());
                act.startActivity(intent);
                return false;
            }
        });
        holder.groupevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ViewReportsListActivity.class);
                intent.putExtra("gid",groupdata.getId());
                intent.putExtra("tittle",groupdata.getTitle());
                act.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }







}
