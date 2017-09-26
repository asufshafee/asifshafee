package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.Review;
import com.example.pk.reviewcollector.R;

import java.util.List;

/**
 * Created by jaani on 9/8/2017.
 */


/**
 * Created by jaani on 9/7/2017.
 */


public class ViewReviewAdopter extends RecyclerView.Adapter<ViewReviewAdopter.MyViewHolder> {

    private List<Review> dataList;
    Context context;
    Activity act;
    private Context mContext;
    private Activity mActivity;
    boolean click = true;

    private RelativeLayout mRelativeLayout;
    private Button mButton;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView username,mesasge,rate;


        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.username);
            mesasge=(TextView) view.findViewById(R.id.txtreview);
            rate = (TextView) view.findViewById(R.id.ratting);
        }

    }



    public ViewReviewAdopter(List<Review> moviesList, Context context, Activity a) {
        this.dataList = moviesList;
        this.context = context;
        this.act=a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_userreview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Review groupdata = dataList.get(position);
        mContext = context;
        mActivity =act;

        // Get the widgets reference from XML layout
        holder.username.setText(dataList.get(position).getUsername());
        holder.mesasge.setText(dataList.get(position).getReviewMessage());
        holder.rate.setText(dataList.get(position).getRating()+"/5");

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }







}
