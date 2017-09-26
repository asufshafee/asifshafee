package com.example.pk.reviewcollector.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pk.reviewcollector.Fragments.grouprequestFragment.OnListFragmentInteractionListener;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MygrouprequestRecyclerViewAdapter extends RecyclerView.Adapter<MygrouprequestRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MygrouprequestRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_grouprequest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        if (flag.equals("data"))
//        {
//            holder.mItem = mValues.get(position);
//            holder.notificationtxt.setText(mValues.get(position).getNotificationMessage());
//
//        }else
//        {
//            holder.notificationtxt.setText("No Notification Found");
//
//        }
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
        public final TextView groupname;
        public final TextView description;
        Button accept,delete;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            groupname = (TextView) view.findViewById(R.id.groupname);
            description = (TextView) view.findViewById(R.id.groupdescription);
            accept=(Button)view.findViewById(R.id.accept);
            delete=(Button)view.findViewById(R.id.delete);

        }


    }
}
