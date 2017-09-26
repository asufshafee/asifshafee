package com.example.pk.reviewcollector.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pk.reviewcollector.Objects.Notification;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.Fragments.notificationFragment.OnListFragmentInteractionListener;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MynotificationRecyclerViewAdapter extends RecyclerView.Adapter<MynotificationRecyclerViewAdapter.ViewHolder> {

    private final List<Notification> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context context;
    String TAG="jhoke";

    public MynotificationRecyclerViewAdapter(List<Notification> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (flag.equals("data"))
        {
            holder.mItem = mValues.get(position);
            holder.notificationtxt.setText(mValues.get(position).getNotificationMessage());

        }else
        {
            holder.notificationtxt.setText("No Notification Found");

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
        public final TextView notificationtxt;
        public Notification mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            notificationtxt = (TextView) view.findViewById(R.id.notificationtxt);
        }

    }
}
