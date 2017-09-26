package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.Notification;
import com.example.pk.reviewcollector.Objects.Report;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.Fragments.newfeedsFragment.OnListFragmentInteractionListener;
import com.example.pk.reviewcollector.Reports;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MynewfeedsRecyclerViewAdapter extends RecyclerView.Adapter<MynewfeedsRecyclerViewAdapter.ViewHolder> {

    private final List<UserNewFeeds> mValues;
    private final OnListFragmentInteractionListener mListener;
    public static final List<UserNewFeeds> dataList = new ArrayList<>();
    Context context1;
    Activity a;
    SharedPreferences prefs;
    UserNewFeeds userNewFeeds;
    public MynewfeedsRecyclerViewAdapter(List<UserNewFeeds> items, OnListFragmentInteractionListener listener,Context context) {
        mValues = items;
        mListener = listener;
        context1=context;


        prefs = context1.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newfeeds, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        if (flag.equals("data"))
        {

            holder.mItem = mValues.get(position);


            holder.mItem = mValues.get(position);
            holder.pilltittle.setText(holder.mItem.getTitle());
            holder.viewReport.setVisibility(View.VISIBLE);
            holder.sharebyadmin.setVisibility(View.VISIBLE);
            holder.viewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context1, Reports.class);
                    intent.putExtra("pollid",holder.mItem.getId());
                    intent.putExtra("tittle",holder.mItem.getTitle());
                    context1.startActivity(intent);
                }
            });

        }else
        {
            holder.pilltittle.setText("No new Feeds");
            holder.viewReport.setVisibility(View.GONE);
            holder.sharebyadmin.setVisibility(View.GONE);


        }
    }

    String flag="nodata";
    @Override
    public int getItemCount() {
        if(mValues.size() == 0){
            flag="nodata";
            return 1;

        }else {
            flag="data";
            return mValues.size();

        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView pilltittle,sharebyadmin;
        Button viewReport;
        public UserNewFeeds mItem;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            pilltittle = (TextView) view.findViewById(R.id.polltittle);
            viewReport=(Button)view.findViewById(R.id.viewreport);
            sharebyadmin=(TextView)view.findViewById(R.id.sharebyadmin);




        }
    }


}
