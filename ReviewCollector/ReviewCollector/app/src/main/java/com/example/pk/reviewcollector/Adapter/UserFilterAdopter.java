package com.example.pk.reviewcollector.Adapter;

/**
 * Created by jaani on 9/10/2017.
 */

        import android.app.Activity;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.support.v7.widget.RecyclerView;
        import android.util.Base64;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
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
        import com.example.pk.reviewcollector.Create_Poll;
        import com.example.pk.reviewcollector.Objects.Grouplist;
        import com.example.pk.reviewcollector.Objects.StaticData;
        import com.example.pk.reviewcollector.Objects.UserSearch;
        import com.example.pk.reviewcollector.R;
        import com.google.gson.Gson;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * Created by jaani on 9/7/2017.
 */


public class UserFilterAdopter extends RecyclerView.Adapter<UserFilterAdopter.MyViewHolder> {

    private List<UserSearch> dataList;
    public static final List<UserSearch> dataListcheck = new ArrayList<>();

    Context context;
    Activity act;
    private Context mContext;
    private Activity mActivity;
    boolean click = true;
    UserSearch userSearch;

    private RelativeLayout mRelativeLayout;
    private Button mButton;

    //    EditText searchgrou;
    Activity a;
    SharedPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final de.hdodenhof.circleimageview.CircleImageView usermage;

        public Button accept;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            accept=(Button)view.findViewById(R.id.join);
            usermage=(de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.userimage);

        }
    }


    String groupid,groupname;
    public UserFilterAdopter(List<UserSearch> moviesList, Context context,Activity a ,String qrouoid,String grouponame)  {
        this.dataList = moviesList;
        groupid=qrouoid;
        this.context = context;
        groupname=grouponame;
        this.act=a;
    }

    @Override
    public com.example.pk.reviewcollector.Adapter.UserFilterAdopter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist, parent, false);

        return new com.example.pk.reviewcollector.Adapter.UserFilterAdopter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.example.pk.reviewcollector.Adapter.UserFilterAdopter.MyViewHolder holder, final int position) {
        final UserSearch userSearch = dataList.get(position);
        mContext = context;
        mActivity =act;
//        byte[] decodedString = Base64.decode(userSearch.getBase64Image(), Base64.DEFAULT);
////        Toast.makeText(getActivity().getApplicationContext(),getRememberimagebase64(), Toast.LENGTH_LONG).show();
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.usermage.setImageBitmap(decodedByte);
        holder.name.setText(userSearch.getName());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Url= StaticData.servername+"AddMember/"+groupid+"/"+userSearch.getUsername();
                Url=Url.replace(" ","@20");
                ActionPerforme(act, Url,"1");
                holder.accept.setText("Joined");
                Url=StaticData.servername+"CreateNotification/"+"Your are added in Group "+groupname+" by Group Admin"+"/No Flag/"+userSearch.getUsername();
                Url=Url.replace(" ","%20");
                ActionPerforme(context,Url,"notification" );
                dataList.remove(position);
                notifyItemRemoved(position);
                //this line below gives you the animation and also updates the
                //list items after the deleted item
                notifyItemRangeChanged(position, getItemCount());
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }




    public  void ActionPerforme(final Context context, final String URL, final String status1)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                if (status1.equals("2"))
                {

//                    JSONArray jsonArray = null;
//                    Gson gson = new Gson();
//                    JSONObject joete = null;
//                    try {
//                        joete = new JSONObject(s);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    dataListcheck.clear();
//                    try {
//
//                        jsonArray = joete.getJSONArray("ViewJoinedGroupsResult");
//                        for (int i=0;i<jsonArray.length();i++)
//                        {
//                            try {
//                                json=jsonArray.getString(i);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            joinGrouplist = gson.fromJson(json, Grouplist.class);
//                            dataListcheck.add(joinGrouplist);
//
//
//                        }
//                    } catch (JSONException e) {
//                        try {
//                            json=joete.getString("ViewJoinedGroupsResult");
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
//                        joinGrouplist = gson.fromJson(json, Grouplist.class);
//                        dataListcheck.add(joinGrouplist);
//
//                        Log.d("","ViewJoinedGroupsResult"+s);
//                    }
//
//
//



                }




            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context,  volleyError.toString(), Toast.LENGTH_LONG).show();;

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
        prefs =act.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    public String getRememberimagebase64() {
        prefs = act.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("image", "");
    }




}


