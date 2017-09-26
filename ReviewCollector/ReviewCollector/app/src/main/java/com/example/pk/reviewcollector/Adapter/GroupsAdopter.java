package com.example.pk.reviewcollector.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pk.reviewcollector.AddMembers;
import com.example.pk.reviewcollector.Create_Poll;
import com.example.pk.reviewcollector.GroupAdminUser;
import com.example.pk.reviewcollector.GroupsArea;
import com.example.pk.reviewcollector.Objects.Group;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.ViewPendingGroupMembers;
import com.example.pk.reviewcollector.ViewReportsListActivity;

import java.util.List;

public class GroupsAdopter extends RecyclerView.Adapter<GroupsAdopter.MyViewHolder> {

    private List<Grouplist> dataList;
    Context context;
    Activity act;
    private Context mContext;
    private Activity mActivity;
    boolean click = true;

    private RelativeLayout mRelativeLayout;
    private Button mButton;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description;
        public Button but;

        public MyViewHolder(View view) {
            super(view);
//            title = (TextView) view.findViewById(R.id.title);
            description=(TextView) view.findViewById(R.id.description);
            title=(TextView) view.findViewById(R.id.groupname1);
            but=(Button)view.findViewById(R.id.groupname);

//            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl);



        }
    }


    public GroupsAdopter(List<Grouplist> moviesList, Context context,Activity a) {
        this.dataList = moviesList;
        this.context = context;
        this.act=a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grouplist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Grouplist groupdata = dataList.get(position);

        mContext = context;

        // Get the activity
        mActivity =act;

        // Get the widgets reference from XML layout


        holder.description.setText(groupdata.getDescription());
        holder.title.setText(groupdata.getTitle());

        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupdata.getStatus().equals("Approved")) {

                    Intent intent=new Intent(context, GroupAdminUser.class);
                    intent.putExtra("tittle",groupdata.getTitle());
                    intent.putExtra("gid",groupdata.getId());
                    act.startActivity(intent);

                }else {
                    Toast.makeText(context,"Group Status is Pending",Toast.LENGTH_LONG).show();
                }


//                WindowViewOFGroup(groupdata.getTitle(),groupdata.getDescription(),groupdata.getRules(),groupdata.getStatus(),groupdata.getId());

            }
        });


    }


    public  void  WindowViewOFGroup(String vname, String vdescription, String vrules, String mstatus, final String ID)
    {


        final Dialog layout = new Dialog(act,android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.windowgroupdetail);

        final TextView name=(TextView)layout.findViewById(R.id.groupname);
        final TextView description=(TextView)layout.findViewById(R.id.groupdescription);
        final TextView rules=(TextView)layout.findViewById(R.id.grouprules);
        final TextView status=(TextView)layout.findViewById(R.id.status);
        final  Button viewpoll=(Button)layout.findViewById(R.id.viewpol);
        final  Button addmembers=(Button)layout.findViewById(R.id.addmembers);
        final  Button viewpanding=(Button)layout.findViewById(R.id.viewpanding);
        viewpanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ViewPendingGroupMembers.class);
                intent.putExtra("gname",dataList.get(0).getTitle());
                intent.putExtra("gid",dataList.get(0).getTitle());
                act.startActivity(intent);

            }
        });

        addmembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddMembers.class);
                intent.putExtra("gid",dataList.get(0).getId());
                intent.putExtra("gname",dataList.get(0).getTitle());

                act.startActivity(intent);
            }
        });

        viewpoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ViewReportsListActivity.class);
                intent.putExtra("gid",dataList.get(0).getId());
                act.startActivity(intent);


            }
        });
        name.setText(vname);
        description.setText(vdescription);
        rules.setText(vrules);
        status.setText(mstatus);

        Button exit=(Button)layout.findViewById(R.id.exit);
        Button create=(Button)layout.findViewById(R.id.createpoll);

        if (status.getText().toString().toLowerCase().equals("pending"))
        {
            create.setEnabled(false);
            Toast.makeText(context,"Group Status is Pending",Toast.LENGTH_LONG).show();
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PollInfoCollect(ID);

            }
        });




        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.dismiss();

            }
        });
        layout.show();

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    public void PollInfoCollect(final String ID)
    {

        final Dialog layout = new Dialog(act,android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.window_collect_poll_info);
        final Spinner noofquestion=(Spinner)layout.findViewById(R.id.NoofQuestions);
        final Spinner noofdays=(Spinner)layout.findViewById(R.id.Noofdays);
        Button btncontinue=(Button)layout.findViewById(R.id.btncontinue);
        final EditText polltittle=(EditText)layout.findViewById(R.id.polltittle);
        final EditText polldescription=(EditText)layout.findViewById(R.id.polldescription);

        final TextView info=(TextView)layout.findViewById(R.id.info);



        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noofquestion.getSelectedItemPosition()==0)
                {
                    info.setText("Please Provide Correct Info.");
                }else if (noofdays.getSelectedItemPosition()==0)
                {
                    info.setText("Please Provide Correct Info.");
                }else if (polltittle.getText().toString().equals(""))
                {
                    polltittle.setError("Please Enter Tittle");
                }else if (polldescription.getText().toString().equals(""))
                {
                    polldescription.setError("Please Enter Description");
                }else
                {
                    layout.dismiss();
                    Intent intent=new Intent(act, Create_Poll.class);
                    Bundle pollinfo=new Bundle();
                    pollinfo.putString("days",noofdays.getSelectedItem().toString());
                    pollinfo.putString("answer","4");
                    pollinfo.putString("question",noofquestion.getSelectedItem().toString());
                    pollinfo.putString("tittle",polltittle.getText().toString());
                    pollinfo.putString("id",ID);
                    pollinfo.putString("description",polldescription.getText().toString());
                    intent.putExtra("info",pollinfo);
                    act.startActivity(intent);
                    layout.dismiss();
                }
            }
        });


        layout.show();
    }



}
