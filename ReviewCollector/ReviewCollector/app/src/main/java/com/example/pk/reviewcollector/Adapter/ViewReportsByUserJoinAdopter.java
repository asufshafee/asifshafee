package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pk.reviewcollector.R;

import java.util.List;

import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.Reports;

public class ViewReportsByUserJoinAdopter extends RecyclerView.Adapter<ViewReportsByUserJoinAdopter.MyViewHolder> {

        private List<UserNewFeeds> dataList;
        Context context;
        Activity act;
        private Context mContext;
        private Activity mActivity;
        boolean click = true;

        private RelativeLayout mRelativeLayout;
        private Button mButton;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public final TextView ststus, tittle,start,end;
            Button viewreport;


            public MyViewHolder(View view) {
                super(view);

                ststus = (TextView) view.findViewById(R.id.status);
                tittle = (TextView) view.findViewById(R.id.polltittle);
                start = (TextView) view.findViewById(R.id.start);
                end = (TextView) view.findViewById(R.id.end);
                viewreport = (Button) view.findViewById(R.id.viewreport);

            }

        }


        public ViewReportsByUserJoinAdopter(List<UserNewFeeds> moviesList, Context context, Activity a) {
            this.dataList = moviesList;
            this.context = context;
            this.act = a;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewreportlist, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final UserNewFeeds userNewFeeds = dataList.get(position);
            mContext = context;
            mActivity = act;


            if (userNewFeeds.getStatus().equals("Active"))
            {
                Log.d("Skiped POlls","Skiped POlls"+userNewFeeds.getTitle());
            }
                holder.tittle.setText(userNewFeeds.getTitle());
                holder.ststus.setText(userNewFeeds.getStatus());
                holder.start.setText(userNewFeeds.getStartedOn());
                holder.end.setText(userNewFeeds.getEndsIn()+" days");
                holder.viewreport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, Reports.class);
                        intent.putExtra("pollid", userNewFeeds.getId());
                        intent.putExtra("tittle", userNewFeeds.getTitle());
                        intent.putExtra("status",userNewFeeds.getStatus());
                        act.startActivity(intent);

                    }
                });



            // Get the widgets reference from XML layout


        }


        @Override
        public int getItemCount() {
            return dataList.size();

        }

    }


