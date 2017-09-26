package com.example.pk.reviewcollector.Adapter;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.Image;
        import android.support.v7.widget.RecyclerView;
        import android.util.Base64;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
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
        import com.example.pk.reviewcollector.Objects.Grouplist;
        import com.example.pk.reviewcollector.Objects.Review;
        import com.example.pk.reviewcollector.Objects.StaticData;
        import com.example.pk.reviewcollector.Objects.User;
        import com.example.pk.reviewcollector.Objects.UserNewFeeds;
        import com.example.pk.reviewcollector.Objects.UserSearch;
        import com.example.pk.reviewcollector.R;
        import com.example.pk.reviewcollector.Reports;
        import com.google.gson.Gson;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * Created by jaani on 9/8/2017.
 */


/**
 * Created by jaani on 9/7/2017.
 */


public class ViewpandingUserAdopter extends RecyclerView.Adapter<ViewpandingUserAdopter.MyViewHolder> {

    private List<UserSearch> dataList;

    Context context;
    Activity act;
    private Context mContext;
    SharedPreferences prefs;
    private Activity mActivity;
    boolean click = true;

    private RelativeLayout mRelativeLayout;
    private Button mButton;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView username;
        public final ImageView profile;

        Button accept,delete;


        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.username);
            profile=(ImageView) view.findViewById(R.id.userimage);
            accept = (Button) view.findViewById(R.id.accept);
            delete = (Button) view.findViewById(R.id.delete);
        }

    }



    String Groupname;
    public ViewpandingUserAdopter(List<UserSearch> moviesList, Context context, Activity a,String groupnamep) {
        this.dataList = moviesList;
        this.context = context;
        this.act=a;
        Groupname=groupnamep;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpendinglist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final UserSearch userSearch = dataList.get(position);
        mContext = context;
        mActivity =act;

        holder.username.setText(userSearch.getName());


//        byte[] decodedString = Base64.decode(userSearch.getBase64Image(), Base64.DEFAULT);
////        Toast.makeText(getActivity().getApplicationContext(),getRememberimagebase64(), Toast.LENGTH_LONG).show();
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.profile.setImageBitmap(decodedByte);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url= StaticData.servername+"DeleteGroupMemberApproved/"+getRememberusertype()+"/"+userSearch.getUsername();
                Url=Url.replace(" ","%20");
                ActionPerforme(act, Url);
                Log.d("","JoinGroup"+Url);
                holder.accept.setText("Joined");

                Url=StaticData.servername+"CreateNotification/"+getRememberusertype()+" Delete Your Group "+ Groupname+" Request/"+"No Flag/"+userSearch.getUsername();
                Url=Url.replace(" ","%20");
                ActionPerforme(context,Url );
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());


                dataList.remove(position);
                notifyItemRemoved(position);
                //this line below gives you the animation and also updates the
                //list items after the deleted item
                notifyItemRangeChanged(position, getItemCount());
                notifyItemRangeChanged(position,dataList.size());
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url= StaticData.servername+"MarkGroupMemberApproved/"+getRememberusertype()+"/"+userSearch.getUsername();;
                Url=Url.replace(" ","%20");
                ActionPerforme(act, Url);
                Log.d("","JoinGroup"+Url);
                holder.accept.setText("Joined");

                Url=StaticData.servername+"CreateNotification/"+getRememberusertype()+" Approved Your Group "+ Groupname+" Request/"+"No Flag/"+userSearch.getUsername();
                Url=Url.replace(" ","%20");
                ActionPerforme(context,Url );
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());


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


    public  void ActionPerforme(final Context context, final String URL)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    JSONArray jsonArray = mainJson.getJSONArray("ApproveGroupResult");
                    Toast.makeText(context,jsonArray.getString(0),Toast.LENGTH_LONG).show();
                    Gson gson = new Gson();


                } catch (JSONException e) {
                    e.printStackTrace();
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







}
