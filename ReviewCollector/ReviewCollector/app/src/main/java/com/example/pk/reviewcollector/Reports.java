package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.Questions;
import com.example.pk.reviewcollector.Objects.QuestionwithOPitions;
import com.example.pk.reviewcollector.Objects.Report;
import com.example.pk.reviewcollector.Objects.Review;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class Reports extends AppCompatActivity {

    LinearLayout layout;
    public static final List<Report> dataList = new ArrayList<>();
        public static final List<QuestionwithOPitions> QUESTIONS = new ArrayList<>();
    QuestionwithOPitions questions;
    TextView op1,op2,op3,op4,op5,op6,pollname;
    TextView op1c,op2c,op3c,op4c,op5c,op6c;
    Button exit,share,next;
    private PieChartView chart;
    private PieChartData data;
    Report report;
    SharedPreferences prefs;
    int start=0,end=0;

    int exitcheck=1;
    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = true;
    private boolean hasCenterText2 = true;
    private boolean isExploded = true;
    private boolean hasLabelForSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        exit=(Button)findViewById(R.id.exit);
        share=(Button)findViewById(R.id.share);
        next=(Button)findViewById(R.id.next);

        op1=(TextView)findViewById(R.id.op1);
        op2=(TextView)findViewById(R.id.op2);
        op3=(TextView)findViewById(R.id.op3);
        op4=(TextView)findViewById(R.id.op4);
        op5=(TextView)findViewById(R.id.op5);
        op6=(TextView)findViewById(R.id.op6);

        op1c=(TextView)findViewById(R.id.op1c);
        op2c=(TextView)findViewById(R.id.op2c);
        op3c=(TextView)findViewById(R.id.op3c);
        op4c=(TextView)findViewById(R.id.op4c);
        op5c=(TextView)findViewById(R.id.op5c);
        op6c=(TextView)findViewById(R.id.op6c);


        pollname=(TextView)findViewById(R.id.pollname);
        if (getrememberusername().equals("admin"))
        {
            share.setVisibility(View.GONE);
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (start<dataList.size()-1)
                {
                    exit.setText("Back");
                    start++;
                    end--;
                    String Url= StaticData.servername+"GetQuestionDetails/"+dataList.get(start).getQuestionId();
                        Url=Url.replace(" ","%20");
                        GetQuestion(getApplicationContext(),Url,"1");

                    Log.d("","datagujratda"+String.valueOf(start)+String.valueOf(end));


                }else {
                    Toast.makeText(getApplicationContext(),"NO More Questions",Toast.LENGTH_LONG).show();
                }


            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getIntent().getExtras().getString("status").equals("Active"))
                {
                    String Url= StaticData.servername+"MarkPollShared/"+getIntent().getExtras().getString("pollid");
                    Url=Url.replace(" ","%20");
                    GetQuestion(getApplicationContext(),Url,"112");
                    Url=StaticData.servername+"CreateNotification/"+getrememberusername()+" Share Poll Report With You/"+getIntent().getExtras().getString("polid")+"/"+"admin";
                    Url=Url.replace(" ","%20");
                    GetQuestion(getApplicationContext(),Url, "4");
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Poll Status is active ",Toast.LENGTH_SHORT).show();
                }


            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start==0)
                finish();
                else
                {
                    if (start!=0)
                    {

                        start--;
                        chart = (PieChartView) findViewById(R.id.chart);
                        String Url= StaticData.servername+"GetQuestionDetails/"+dataList.get(start).getQuestionId();
                        Log.d("","malikasof"+StaticData.servername+"GetQuestionDetails/"+dataList.get(start).getQuestionId());
                        Url=Url.replace(" ","%20");
                        GetQuestion(getApplicationContext(),Url,"1");
                        end++;
                        Log.d("","datagujratda"+String.valueOf(start)+String.valueOf(end));
                        if (start==0)
                            exit.setText("Exit");

                    }else {
                        Toast.makeText(getApplicationContext(),"No More Questions",Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


        getSupportActionBar().setTitle("Reports");
        chart = (PieChartView) findViewById(R.id.chart);
        String Url= StaticData.servername+"GetReport/"+getIntent().getExtras().getString("pollid");
        Url=Url.replace(" ","%20");
        GetReport(getApplicationContext(),Url );



    }

    private void generateData() {
        int numValues = 0;

        if (!QUESTIONS.get(0).getOption6().equals("null"))
        {
            Log.d("","Questionwhynot"+"1");
            numValues=6;


        }else  if (!QUESTIONS.get(0).getOption5().equals("null"))
        {
            Log.d("","Questionwhynot"+"2");
            numValues=5;
            op6.setVisibility(View.GONE);

            op6c.setVisibility(View.GONE);
        }
        else  if (!QUESTIONS.get(0).getOption4().equals("null"))
        {
            Log.d("","Questionwhynot"+"3");
            numValues=4;
            op5.setVisibility(View.GONE);
            op6.setVisibility(View.GONE);


            op5c.setVisibility(View.GONE);
            op6c.setVisibility(View.GONE);
        }
        else  if (!QUESTIONS.get(0).getOption3().equals("null"))
        {
            Log.d("","Questionwhynot"+"4");

            numValues=3;
            op4.setVisibility(View.GONE);
            op5.setVisibility(View.GONE);
            op6.setVisibility(View.GONE);

            op4c.setVisibility(View.GONE);
            op5c.setVisibility(View.GONE);
            op6c.setVisibility(View.GONE);
        }else {

            Log.d("","Questionwhynot"+"5");
            numValues=2;
            op3.setVisibility(View.GONE);
            op4.setVisibility(View.GONE);
            op5.setVisibility(View.GONE);
            op6.setVisibility(View.GONE);


            op3c.setVisibility(View.GONE);
            op4c.setVisibility(View.GONE);
            op5c.setVisibility(View.GONE);
            op5c.setVisibility(View.GONE);

        }

        List<SliceValue> values = new ArrayList<SliceValue>();
//        for (int i = 0; i < numValues; ++i) {
//            SliceValue sliceValue = new SliceValue((float) i, ChartUtils.pickColor());
//            Log.d("math","Math"+ Math.random());
//            values.add(sliceValue);
//        }
        int total=0;
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = null;
            int color;
            if (i==0)
            {
                color= ChartUtils.pickColor();
                sliceValue = new SliceValue((float) Integer.parseInt(dataList.get(start).getOption1()), Color.BLUE);
                op1c.setBackgroundColor(Color.BLUE);
                total=total+Integer.parseInt(dataList.get(start).getOption1());

            }else    if (i==1)
            {
                color= ChartUtils.pickColor();
                sliceValue = new SliceValue((float) Integer.parseInt(dataList.get(start).getOption2()),Color.RED);
                op2c.setBackgroundColor(Color.RED);
                total=total+Integer.parseInt(dataList.get(start).getOption2());
                Log.d("","total"+String.valueOf(total));


            }
            else    if (i==2)
            {
                color= ChartUtils.pickColor();
                sliceValue = new SliceValue((float) Integer.parseInt(dataList.get(start).getOption3()),Color.YELLOW);
                op3c.setBackgroundColor(Color.YELLOW);
                total=total+Integer.parseInt(dataList.get(start).getOption3());


            }
            else    if (i==3)
            {
                color= ChartUtils.pickColor();
                sliceValue = new SliceValue((float) Integer.parseInt(dataList.get(start).getOption4()),Color.BLACK);
                op4c.setBackgroundColor(Color.BLACK);
                total=total+Integer.parseInt(dataList.get(start).getOption4());


            } else    if (i==4)
            {
                color= ChartUtils.pickColor();
                sliceValue = new SliceValue((float) Integer.parseInt(dataList.get(start).getOption5()),Color.GREEN);
                op5c.setBackgroundColor(Color.GREEN);
                total=total+Integer.parseInt(dataList.get(start).getOption5());

            }
            else    if (i==5)
            {
                color= ChartUtils.pickColor();
                sliceValue = new SliceValue((float) Integer.parseInt(dataList.get(start).getOption6()),Color.LTGRAY);
                op6c.setBackgroundColor(Color.LTGRAY);
                total=total+Integer.parseInt(dataList.get(start).getOption6());


            }
            values.add(sliceValue);

        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setValueLabelBackgroundColor(ChartUtils.pickColor());
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(3);
        }

        if (hasCenterText1) {
            data.setCenterText1("Total");

            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.text_margin)));
        }

        if (hasCenterText2) {
            data.setCenterText2(String.valueOf(total));

//            Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "Roboto-Italic.ttf");

//            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.text_margin)));
        }

        chart.setPieChartData(data);
    }

    public  void GetReport(final Context context, final String URL)
    {


        //sending image to server
        final StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                JSONArray jsonArray = null;
                Gson gson = new Gson();
                JSONObject joete = null;
                try {
                    joete = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dataList.clear();
                try {

                    jsonArray = joete.getJSONArray("GetReportResult");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        try {
                            json=jsonArray.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        report = gson.fromJson(json, Report.class);
                        dataList.add(report);


                    }
                } catch (JSONException e) {
                    try {
                        json=joete.getString("GetReportResult");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    report = gson.fromJson(json, Report.class);
                    dataList.add(report);

                    Log.d("","GetReportResult"+s);
                }
                Log.d("","GetQuestionDetailsResult"+dataList.size());

                start=0;
                end=dataList.size();

                chart = (PieChartView) findViewById(R.id.chart);
                String Url= StaticData.servername+"GetQuestionDetails/"+dataList.get(0).getQuestionId();
                Url=Url.replace(" ","%20");
                GetQuestion(getApplicationContext(),Url,"1");







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

    public  void GetQuestion(final Context context, final String URL, final String check)
    {


        op3.setVisibility(View.VISIBLE);
        op4.setVisibility(View.VISIBLE);
        op5.setVisibility(View.VISIBLE);
        op6.setVisibility(View.VISIBLE);
        op3c.setVisibility(View.VISIBLE);
        op4c.setVisibility(View.VISIBLE);
        op5c.setVisibility(View.VISIBLE);
        op6c.setVisibility(View.VISIBLE);

        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                if (check.equals("1"))
                {
                    JSONArray jsonArray = null;
                    Gson gson = new Gson();
                    JSONObject joete = null;
                    try {
                        joete = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    QUESTIONS.clear();
                    try {

                        jsonArray = joete.getJSONArray("GetQuestionDetailsResult");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                json=jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            questions = gson.fromJson(json, QuestionwithOPitions.class);
                            QUESTIONS.add(questions);


                        }
                    } catch (JSONException e) {
                        try {
                            json=joete.getString("GetQuestionDetailsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        questions = gson.fromJson(json, QuestionwithOPitions.class);
                        QUESTIONS.add(questions);

                        Log.d("","GetQuestionDetailsResult"+s);
                    }
                    Log.d("","GetQuestionDetailsResult"+QUESTIONS.size());

                    op1.setText(QUESTIONS.get(0).getOption1());
                    op2.setText(QUESTIONS.get(0).getOption2());
                    op3.setText(QUESTIONS.get(0).getOption3());
                    op4.setText(QUESTIONS.get(0).getOption4());
                    op5.setText(QUESTIONS.get(0).getOption5());
                    op6.setText(QUESTIONS.get(0).getOption6());
                    pollname.setText(QUESTIONS.get(0).getQuestionTitle());
                    generateData();

                }else  if (check.equals("2"))
                {
                    try {
                        JSONObject mainJson = new JSONObject(s);
                        json=mainJson.getString("GetReportResult");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();

                    report = gson.fromJson(json, Report.class);
                }








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
    public String getrememberusername() {
        prefs =this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
