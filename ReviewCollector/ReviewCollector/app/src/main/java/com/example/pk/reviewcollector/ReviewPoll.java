package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.Objects.Questions;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.util.ObjectSerializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewPoll extends AppCompatActivity {


    Button next,backorexit;
    RadioButton op1,op2,op3,op4,op5,op6;
    TextView polltittle,status,pollquestion;
    Intent intent;
    Bundle bundle;
    UserNewFeeds userNewFeeds;
    Questions questions;
    public static final List<Questions> QUESTIOSNS = new ArrayList<>();
    int position=0,total=0;
    int questionposition=1;
    int finaltotal=0;
    String[] answers ;
    RadioGroup radioGroupOpitions;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_poll);

        prefs = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        next=(Button)findViewById(R.id.next);
        backorexit=(Button)findViewById(R.id.exit);


        op1=(RadioButton)findViewById(R.id.op1);
        op2=(RadioButton)findViewById(R.id.op2);
        op3=(RadioButton)findViewById(R.id.op3);
        op4=(RadioButton)findViewById(R.id.op4);
        op5=(RadioButton)findViewById(R.id.op5);
        op6=(RadioButton)findViewById(R.id.op6);


        radioGroupOpitions=(RadioGroup)findViewById(R.id.radiogroupOpitions);


        polltittle=(TextView)findViewById(R.id.pollname);
        pollquestion=(TextView)findViewById(R.id.pollquestion);

        status=(TextView)findViewById(R.id.status);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (total>=1 )
                {

                    if (op1.isChecked())
                    {
                     answers[position-1]="1";
                    }else if (op2.isChecked())
                    {
                        answers[position-1]="2";
                    }else  if (op3.isChecked())
                    {
                        answers[position-1]="3";
                    }else  if (op4.isChecked())
                    {
                        answers[position-1]="4";
                    }
                    else  if (op5.isChecked())
                    {
                        answers[position-1]="5";
                    }
                    else  if (op6.isChecked())
                    {
                        answers[position-1]="6";
                    }
//                    Log.d("","answer"+answers[position]);

                    op1.setText(QUESTIOSNS.get(position-1).getOp2());
                    op2.setText(QUESTIOSNS.get(position-1).getOp3());
                    op3.setText(QUESTIOSNS.get(position-1).getOp4());
                    op4.setText(QUESTIOSNS.get(position-1).getOp5());
                    op5.setText(QUESTIOSNS.get(position-1).getOp6());
                    op6.setText(QUESTIOSNS.get(position-1).getOp7());

                    if (QUESTIOSNS.get(position-1).getOp4().equals("null"))
                    {
                        op3.setVisibility(View.GONE);
                    }else
                    {
                        op3.setVisibility(View.VISIBLE);
                    }
                    if (QUESTIOSNS.get(position-1).getOp5().equals("null"))
                    {
                        op4.setVisibility(View.GONE);
                    }else {
                        op4.setVisibility(View.VISIBLE);
                    }
                    if (QUESTIOSNS.get(position-1).getOp6().equals("null"))
                    {
                        op5.setVisibility(View.GONE);
                    }else {
                        op5.setVisibility(View.VISIBLE);
                    }
                    if (QUESTIOSNS.get(position-1).getOp7().equals("null"))
                    {
                        op6.setVisibility(View.GONE);
                    }else {
                        op6.setVisibility(View.VISIBLE);
                    }
                    pollquestion.setText(QUESTIOSNS.get(position-1).getOp1());
                    total--;
                    backorexit.setText("Back");
                    if (total==0)
                     {
                         next.setText("Submit");
                     }else {
                         questionposition++;
                         position++;
                         status.setText(String.valueOf(questionposition)+"/"+String.valueOf(finaltotal));
                     }
                }else if (next.getText().equals("Submit"))
                {
                    if (position!=0)
                    {

                        for (int i=0;i<finaltotal;i++)
                        {
                            String Url=StaticData.servername+"CastVote/"+getRememberusertype()+"/"+QUESTIOSNS.get(i).getQuestion()+"/"+answers[i]+"/"+userNewFeeds.getId();
                            Url=Url.replace(" ","%20");
                            Log.d("",Url);
                            ActionPerforme(getApplicationContext(),Url);
                        }
//
//                         String Url=StaticData.servername+"CreateNotification/"+getRememberusertype()+" Reviewed on your Poll "+userNewFeeds.getTitle()+"/"+"No Flag/"+userNewFeeds.get;
//                        Url=Url.replace(" ","%20");
//                        ActionPerforme(progressDialog,getApplicationContext(),Url);
                    }

                    Intent intent=new Intent(getApplicationContext(),new_feeds.class);
                    startActivity(intent);
                    finish();

                }

            }
        });
        backorexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position!=0){

                    position--;
                    total++;


                    if (QUESTIOSNS.get(position).getOp4().equals("null"))
                    {
                        op3.setVisibility(View.GONE);
                    }else
                    {
                        op3.setVisibility(View.VISIBLE);
                    }
                    if (QUESTIOSNS.get(position).getOp5().equals("null"))
                    {
                        op4.setVisibility(View.GONE);
                    }else {
                        op4.setVisibility(View.VISIBLE);
                    }
                    if (QUESTIOSNS.get(position).getOp6().equals("null"))
                    {
                        op5.setVisibility(View.GONE);
                    }else {
                        op5.setVisibility(View.VISIBLE);
                    }
                    if (QUESTIOSNS.get(position).getOp7().equals("null"))
                    {
                        op6.setVisibility(View.GONE);
                    }else {
                        op6.setVisibility(View.VISIBLE);
                    }
                    op1.setText(QUESTIOSNS.get(position).getOp2());
                    op2.setText(QUESTIOSNS.get(position).getOp3());
                    op3.setText(QUESTIOSNS.get(position).getOp4());
                    op4.setText(QUESTIOSNS.get(position).getOp5());
                    pollquestion.setText(QUESTIOSNS.get(position).getOp1());
                    questionposition--;
                    status.setText(String.valueOf(questionposition)+"/"+String.valueOf(finaltotal));
                    if (total==finaltotal)
                        backorexit.setText("Exit");


                }else if (backorexit.getText().equals("Back"))
                {
                    backorexit.setText("Exit");

                }else if (backorexit.getText().equals("Exit"))
                {
                    Intent intent=new Intent(getApplicationContext(),new_feeds.class);
                    startActivity(intent);
                    finish();
                }

            }
        });







        Gson gson = new Gson();
        questions=new Questions();

        userNewFeeds = gson.fromJson(getIntent().getStringExtra("obj").toString(),UserNewFeeds.class);
        getSupportActionBar().setTitle(userNewFeeds.getTitle());

        polltittle.setText(userNewFeeds.getTitle());

        QUESTIOSNS.clear();

        try {
            JSONObject jsonObject=new JSONObject(getIntent().getStringExtra("obj").toString());
            JSONArray jsonArray1=jsonObject.getJSONArray("Questions");
            for (int i=0;i<jsonArray1.length();i++)
            {
                JSONArray jsonArray=jsonArray1.getJSONArray(i);
                Log.d("","BINLADINJS123"+jsonArray.toString());

                Questions questions=new Questions();
                questions.setQuestion(jsonArray.get(0).toString());
                questions.setOp1(jsonArray.get(1).toString());
                questions.setOp2(jsonArray.get(2).toString());
                questions.setOp3(jsonArray.get(3).toString());
                questions.setOp4(jsonArray.get(4).toString());
                questions.setOp5(jsonArray.get(5).toString());
                questions.setOp6(jsonArray.get(6).toString());
                questions.setOp7(jsonArray.get(7).toString());
                QUESTIOSNS.add(questions);
                total++; finaltotal++;
            }

            status.setText(String.valueOf(questionposition)+"/"+String.valueOf(total));




            op1.setText(QUESTIOSNS.get(0).getOp2());
            op2.setText(QUESTIOSNS.get(0).getOp3());
            op3.setText(QUESTIOSNS.get(0).getOp4());
            op4.setText(QUESTIOSNS.get(0).getOp5());
            op5.setText(QUESTIOSNS.get(0).getOp6());
            op6.setText(QUESTIOSNS.get(0).getOp7());
            if (QUESTIOSNS.get(0).getOp4().equals("null"))
            {
                op3.setVisibility(View.GONE);
            }else
            {
                op3.setVisibility(View.VISIBLE);
            }
            if (QUESTIOSNS.get(0).getOp5().equals("null"))
            {
                op4.setVisibility(View.GONE);
            }else {
                op4.setVisibility(View.VISIBLE);
            }
            if (QUESTIOSNS.get(0).getOp6().equals("null"))
            {
                op5.setVisibility(View.GONE);
            }else {
                op5.setVisibility(View.VISIBLE);
            }
            if (QUESTIOSNS.get(0).getOp7().equals("null"))
            {
                op6.setVisibility(View.GONE);
            }else {
                op6.setVisibility(View.VISIBLE);
            }
            pollquestion.setText(QUESTIOSNS.get(0).getOp1());
            answers=new String[QUESTIOSNS.size()];

                position++;



        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"NO Question added By admin",Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }


    }

    public  void ActionPerforme( final Context context, final String URL)
    {



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("LoginResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();

//                user = gson.fromJson(json, User.class);
                Log.d("backi","sending result"+json);


            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }
    public String getRememberusertype() {
        prefs =getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
