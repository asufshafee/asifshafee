package com.example.pk.reviewcollector.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pk.reviewcollector.GroupsArea;
import com.example.pk.reviewcollector.Login;
import com.example.pk.reviewcollector.MyApplication;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.ReviewApp;
import com.example.pk.reviewcollector.ViewJoinGroups;
import com.example.pk.reviewcollector.ViewProfile;
import com.example.pk.reviewcollector.ViewReviewAdmin;
import com.example.pk.reviewcollector.util.WCFHandler;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    de.hdodenhof.circleimageview.CircleImageView profile;
    MyApplication app;
    User user;

    Bitmap bitmap1;

    EditText name, email;
    SharedPreferences prefs;
    Button Lagout, gropus, joingroups, editprofile, reviewapp;
    LinearLayout linearLayout;

    public Profile() {
        // Required empty public constructor
    }

//    @Override
//    public void onDetach() {
//        linearLayout.setVisibility(View.GONE);
//        super.onDetach();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image);


        MyTaskLogin download = new MyTaskLogin();
        download.execute();

//        download.execute(getRememberimage()+".jpg");
//        Log.d("","path"+getRememberimage()+".jpg");
//        File file=download.getfile();
//
//        if(file.exists())
//        {
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            profile.setImageBitmap(myBitmap);
//        }

        editprofile=(Button)view.findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ViewProfile.class);
                startActivity(intent);
            }
        });
        prefs = getActivity().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        Lagout = (Button) view.findViewById(R.id.lagout);
        gropus = (Button) view.findViewById(R.id.gropus);
        reviewapp = (Button) view.findViewById(R.id.review);
        reviewapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRememberusertype().equals("admin")) {
                    Intent intent = new Intent(getActivity(), ViewReviewAdmin.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getActivity(), ReviewApp.class);
                    startActivity(intent);
                }

            }
        });
        gropus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupsArea.class);
                startActivity(intent);
            }
        });
        joingroups = (Button) view.findViewById(R.id.joingropus);
        joingroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ViewJoinGroups.class);
                startActivity(intent);

            }
        });

        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        name.setText(getname());
        email.setText(getemail());

        if (!getRememberusertype().equals("admin")) {
            gropus.setVisibility(View.VISIBLE);
            joingroups.setVisibility(View.VISIBLE);
            reviewapp.setText("Review App");
        } else {
            gropus.setVisibility(View.GONE);
            joingroups.setVisibility(View.GONE);

            reviewapp.setText("View Reviews");
        }
        Lagout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("islogin", false);
                editor.apply();
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });


//        byte[] decodedString = Base64.decode(getRememberimagebase64(), Base64.DEFAULT);
////        Toast.makeText(getActivity().getApplicationContext(),getRememberimagebase64(), Toast.LENGTH_LONG).show();
//        Bitmap decodedByte = BitmapFactory.decodeFile(getRememberimagebase64());
//        profile.setImageBitmap(decodedByte);

        File imgFile = new  File("/storage/sdcard/Android/data/com.example.pk.reviewcollector/files/"+getRememberimagebase64());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            profile.setImageBitmap(myBitmap);

        }

        return view;
    }

    public String getRememberimage() {
        prefs = getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    public String getRememberimagebase64() {
        prefs = getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("image", "");
    }
    public String getRememberusertype() {
        prefs = getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("usertype", "");
    }

    public String getname() {
        prefs = getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("name", "");
    }

    public String getemail() {
        prefs = getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("remember_email", "");
    }


    ///


    private class MyTaskLogin extends AsyncTask<String, Void, Bitmap> {
        WCFHandler wcfHandler = new WCFHandler();

        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                bitmap = wcfHandler.downloadImages("/", "Anonymous", "Anonymous", getRememberimage() + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);


            if (null == result) {
            } else {
                profile.setImageBitmap(result);

            }


        }
    }
}
