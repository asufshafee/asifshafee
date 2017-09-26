package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Fragments.userreviewFragment.OnListFragmentInteractionListener;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.Review;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.R;
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
public class MyuserreviewRecyclerViewAdapter extends RecyclerView.Adapter<MyuserreviewRecyclerViewAdapter.ViewHolder> {

    private final List<Review> mValues;
    private final OnListFragmentInteractionListener mListener;









    public MyuserreviewRecyclerViewAdapter(List<Review> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_userreview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.username.setText(mValues.get(position).getUsername());
        holder.txtreview.setText(mValues.get(position).getReviewMessage());
        holder.ratting.setText(mValues.get(position).getRating()+"/5");


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView username;
        public final TextView txtreview,ratting;
        public Review mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            username = (TextView) view.findViewById(R.id.username);
            txtreview = (TextView) view.findViewById(R.id.txtreview);
            ratting = (TextView) view.findViewById(R.id.ratting);


        }

        }

}
