package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.util.ObjectSerializer;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Create_Poll extends AppCompatActivity {

    Intent intent;
    Bundle bundle;
    int question=0,answer,realquestion;


    EditText op1,op2,op3,op4,op5,op6;
    String numbersJson;
    EditText questiontextfiled;
    TextView Questuonuodate;
    Button btnpollcreater;
    String [][] pollquestions;
    String [] pollquestionopitionslenght;
    JSONObject jsonObject;
    Spinner selectopitions;
    int uodatequestion1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__poll);
        intent = getIntent();


        op1 = (EditText) findViewById(R.id.op1);
        op2 = (EditText) findViewById(R.id.op2);
        op3 = (EditText) findViewById(R.id.op3);
        op4 = (EditText) findViewById(R.id.op4);
        op5 = (EditText) findViewById(R.id.op5);
        op6 = (EditText) findViewById(R.id.op6);

        Questuonuodate=(TextView)findViewById(R.id.questionupdate);
        selectopitions=(Spinner)findViewById(R.id.Noofopitions);
        op3.setVisibility(View.GONE);
        op4.setVisibility(View.GONE);
        selectopitions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectopitions.getSelectedItemPosition()==0)
                {
                    op3.setVisibility(View.GONE);
                    op4.setVisibility(View.GONE);
                    op1.setVisibility(View.GONE);
                    op2.setVisibility(View.GONE);
                    op5.setVisibility(View.GONE);
                    op6.setVisibility(View.GONE);
                }else
                {
                    if (selectopitions.getSelectedItemPosition()==2)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                    }else if (selectopitions.getSelectedItemPosition()==3)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);

                    }else if (selectopitions.getSelectedItemPosition()==4)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op5.setVisibility(View.VISIBLE);


                    }
                    else if (selectopitions.getSelectedItemPosition()==5)
                    {
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        op5.setVisibility(View.VISIBLE);
                        op6.setVisibility(View.VISIBLE);

                    }else {

                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                    }
                }

                // do stuff
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        getSupportActionBar().setTitle("Create Poll");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        questiontextfiled = (EditText) findViewById(R.id.question);
        btnpollcreater = (Button) findViewById(R.id.btnpollcreater);
        jsonObject = new JSONObject();

        btnpollcreater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question <= realquestion) {

                    pollquestions[question][0] = questiontextfiled.getText().toString();
                    pollquestions[question][1] = op1.getText().toString();
                    pollquestions[question][2] = op2.getText().toString();
                    pollquestions[question][3] = op3.getText().toString();
                    pollquestions[question][4] = op4.getText().toString();
                    pollquestions[question][5] = op5.getText().toString();
                    pollquestions[question][6] = op6.getText().toString();

                    if (selectopitions.getSelectedItemPosition() == 0) {
                        Toast.makeText(getApplicationContext(), "Please Select Opitions", Toast.LENGTH_LONG).show();

                    }else {


                        if (selectopitions.getSelectedItemPosition() == 1) {
                            if (!op1.getText().toString().equals("") && !op2.getText().toString().equals("")) {
                                pollquestions[question][0] = questiontextfiled.getText().toString();
                                pollquestions[question][1] = op1.getText().toString();
                                pollquestions[question][2] = op2.getText().toString();
                                pollquestions[question][3] = "null";
                                pollquestions[question][4] = "null";
                                pollquestions[question][5] = "null";
                                pollquestions[question][6] = "null";
                                op3.setVisibility(View.GONE);
                                op4.setVisibility(View.GONE);
                                op5.setVisibility(View.GONE);
                                op6.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(getApplicationContext(), "Please Fill Options", Toast.LENGTH_LONG).show();

                            }

                        } else if (selectopitions.getSelectedItemPosition() == 2) {
                            if (!op1.getText().toString().equals("") && !op2.getText().toString().equals("") && !op3.getText().toString().equals("")) {
                                pollquestions[question][0] = questiontextfiled.getText().toString();
                                pollquestions[question][1] = op1.getText().toString();
                                pollquestions[question][2] = op2.getText().toString();
                                pollquestions[question][3] = op3.getText().toString();
                                ;
                                pollquestions[question][4] = "null";
                                pollquestions[question][5] = "null";
                                pollquestions[question][6] = "null";
                                op4.setVisibility(View.GONE);
                                op5.setVisibility(View.GONE);
                                op6.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(getApplicationContext(), "Please Fill Options", Toast.LENGTH_LONG).show();

                            }

                        } else if (selectopitions.getSelectedItemPosition() == 3) {
                            if (!op1.getText().toString().equals("") && !op2.getText().toString().equals("") && !op3.getText().toString().equals("") && !op4.getText().toString().equals("")) {
                                pollquestions[question][0] = questiontextfiled.getText().toString();
                                pollquestions[question][1] = op1.getText().toString();
                                pollquestions[question][2] = op2.getText().toString();
                                pollquestions[question][3] = op3.getText().toString();
                                ;
                                pollquestions[question][4] = op4.getText().toString();
                                pollquestions[question][5] = "null";
                                pollquestions[question][6] = "null";
                                op5.setVisibility(View.GONE);
                                op6.setVisibility(View.GONE);
                            }
                        } else if (selectopitions.getSelectedItemPosition() == 4) {
                            if (!op1.getText().toString().equals("") && !op2.getText().toString().equals("") && !op3.getText().toString().equals("") && !op4.getText().toString().equals("") && !op5.getText().equals("")) {
                                pollquestions[question][0] = questiontextfiled.getText().toString();
                                pollquestions[question][1] = op1.getText().toString();
                                pollquestions[question][2] = op2.getText().toString();
                                pollquestions[question][3] = op3.getText().toString();
                                pollquestions[question][4] = op4.getText().toString();
                                pollquestions[question][5] = op5.getText().toString();
                                pollquestions[question][6] = "null";
                                op6.setVisibility(View.GONE);
                            }
                        } else if (selectopitions.getSelectedItemPosition() == 5) {
                            if (!op1.getText().toString().equals("") && !op2.getText().toString().equals("") && !op3.getText().toString().equals("") && !op4.getText().toString().equals("") && !op5.getText().toString().equals("") && !op6.getText().toString().equals("")) {
                                pollquestions[question][0] = questiontextfiled.getText().toString();
                                pollquestions[question][1] = op1.getText().toString();
                                pollquestions[question][2] = op2.getText().toString();
                                pollquestions[question][3] = op3.getText().toString();
                                pollquestions[question][4] = op4.getText().toString();
                                pollquestions[question][5] = op5.getText().toString();
                                pollquestions[question][6] = op6.getText().toString();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Fill Options", Toast.LENGTH_LONG).show();

                            }

                        }


                        question = question + 1;
                        if (question > realquestion) {
                            btnpollcreater.setText("Continue");
                        } else {
                            uodatequestion1++;
                            Questuonuodate.setText("Question No. " + String.valueOf(uodatequestion1));
                            questiontextfiled.setText("");
                            op1.setText("");
                            op2.setText("");
                            op3.setText("");
                            op4.setText("");
                            op5.setText("");
                            op6.setText("");
                        }
                    }






                }
                else {
                    Gson gson = new Gson();

                    // Convert numbers array into JSON string.
                    numbersJson = gson.toJson(pollquestions);
                    // Convert strings array into JSON string

                    Log.d("", "INFO" + numbersJson);


                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDateandTime = sdf.format(new Date());
                        String questions = ObjectSerializer.serialize(pollquestions);
                        ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(Create_Poll.this);
                        progressDialog.setMessage("please wait...");
                        progressDialog.show();

                        String repleae = StaticData.servername + "CreatePoll/" + bundle.getString("tittle") + "/" + bundle.getString("description") + "/" + currentDateandTime + "/" + bundle.getString("days") + "/" + bundle.getString("id");
                        repleae = repleae.replace(" ", "%20");
                        ActionPerforme(progressDialog, getApplicationContext(), repleae, "1");
                        Log.d("", "INFOUrl" + repleae);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


        bundle = intent.getBundleExtra("info");


        realquestion = Integer.parseInt(bundle.getString("question"));
        answer = 6;

        pollquestions = new String[realquestion][answer+1];
        pollquestionopitionslenght=new String[10];
        realquestion = realquestion - 1;

    }


    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL , final String check)
    {
        progressDialog.setMessage("please wait...");
        progressDialog.show();



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("CreatePollResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("", "CreatePollResult" + s);



                if (check=="1")
                {
                    String op1="no",op2="no",op3="no",op4="no",op5="no",op6="no",sendquestion="no";

                    question=0;
                    for (int i=0;i<realquestion+1;i++)
                    {


                            if (answer==2)
                            {
                                sendquestion=pollquestions[question][0];
                                op1=pollquestions[question][1];
                                op2=pollquestions[question][2];
                                op3=pollquestions[question][3];
                                op4=pollquestions[question][4];
                                op5=pollquestions[question][5];
                                op6=pollquestions[question][6];

                            }else if (answer==3)
                            {
                                sendquestion=pollquestions[question][0];
                                op1=pollquestions[question][1];
                                op2=pollquestions[question][2];
                                op3=pollquestions[question][3];
                                op4=pollquestions[question][4];
                                op5=pollquestions[question][5];
                                op6=pollquestions[question][6];

                            }else if (answer==4)
                            {
                                sendquestion=pollquestions[question][0];
                                op1=pollquestions[question][1];
                                op2=pollquestions[question][2];
                                op3=pollquestions[question][3];
                                op4=pollquestions[question][4];
                                op5=pollquestions[question][5];
                                op6=pollquestions[question][6];
                            }
                            else if (answer==5)
                            {
                                sendquestion=pollquestions[question][0];
                                op1=pollquestions[question][1];
                                op2=pollquestions[question][2];
                                op3=pollquestions[question][3];
                                op4=pollquestions[question][4];
                                op5=pollquestions[question][5];
                                op6=pollquestions[question][6];
                            }
                            else if (answer==6)
                            {
                                sendquestion=pollquestions[question][0];
                                op1=pollquestions[question][1];
                                op2=pollquestions[question][2];
                                op3=pollquestions[question][3];
                                op4=pollquestions[question][4];
                                op5=pollquestions[question][5];
                                op6=pollquestions[question][6];
                            }
                            question++;

                        Log.d("", "CreatePollResult12" + StaticData.servername+"InsertQuestion/"+json+"/"+sendquestion+"/"+op1+"/"+op2+"/"+op3+"/"+op4+"/"+op4+"/"+op4);

                        String Url=StaticData.servername+"InsertQuestion/"+json+"/"+sendquestion+"/"+op1+"/"+op2+"/"+op3+"/"+op4+"/"+op5+"/"+op6;
                        Url=Url.replace(" ","%20");
                        UploadQuestions(context,Url,"1");
                    }
                    finish();
                    Log.d("", "CreatePollResult234567" + StaticData.servername+"InsertQuestion/"+json+"/"+sendquestion+"/"+op1+"/"+op2+"/"+op3+"/"+op4+"/"+op4+"/"+op4);

                }



            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                progressDialog.cancel();
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("questions",numbersJson);
                    return parameters;
            }
        };


        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }


    public  void UploadQuestions( final Context context, final String URL , final String check)
    {



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("CreatePollResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("", "CreatePollResult" + s);




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
                parameters.put("questions",numbersJson);
                return parameters;
            }
        };


        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {

            finish();
        } else {
            Toast.makeText(getBaseContext(), "Are You Sure. if Yes .Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();


    }

}

