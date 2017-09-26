//package com.example.pk.reviewcollector.dummy;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.pk.reviewcollector.Objects.PandingGroup;
//import com.example.pk.reviewcollector.Objects.StaticData;
//import com.google.gson.Gson;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Helper class for providing sample content for user interfaces created by
// * Android template wizards.
// * <p>
// * TODO: Replace all uses of this class before publishing your app.
// */
//public class DummyContent1 {
//
//
//
//    PandingGroup pandingGroup;
//
//    public DummyContent1(Context context)
//    {
//
//        ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("please wait...");
//        pandingGroup=new PandingGroup();
//        ActionPerforme(progressDialog,context, StaticData.servername+"ViewPendingGroups");
////        Log.d("","jaani"+StaticData.servername+"ViewPendingGroups");
//    }
//    /**
//     * An array of sample (dummy) items.
//     */
//    public static final List<PandingGroup> ITEMS = new ArrayList<PandingGroup>();
//
//    /**
//     * A map of sample (dummy) items, by ID.
//     */
//    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
//
//    private static final int COUNT = 25;
//
////    static {
////        // Add some sample items.
////        for (int i = 1; i <= COUNT; i++) {
////            addItem(createDummyItem(i));
////        }
////    }
////
////    private static void addItem(PandingGroup item) {
////        ITEMS.add(item);
////        ITEM_MAP.put(item.Id, item);
////    }
//
////    private static DummyItem createDummyItem(int position) {
////
////    }
//
////    private static String makeDetails(int position) {
////        StringBuilder builder = new StringBuilder();
////        builder.append("Details about Item: ").append(position);
////        for (int i = 0; i < position; i++) {
////            builder.append("\nMore details information here.");
////        }
////        return builder.toString();
////    }
//
//    /**
//     * A dummy item representing a piece of content.
//     */
//    public static class DummyItem {
//        public final String Id;
//        public final String Admin;
//        public final String Description;
//
//        public DummyItem(String Id, String Admin, String Description) {
//            this.Id = Id;
//            this.Admin = Admin;
//            this.Description = Description;
//        }
//
////        @Override
////        public String toString() {
////            return content;
////        }
//
//    }
//
//    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL)
//    {
//        progressDialog.setMessage("Uploading, please wait...");
////        progressDialog.show();
//
//
//
//
//        //sending image to server
//        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String s) {
//                progressDialog.dismiss();
//                String json = s;
//                try {
//                    JSONObject mainJson = new JSONObject(s);
//                    JSONArray jsonArray = mainJson.getJSONArray("ViewPendingGroupsResult");
//
//                    Gson gson = new Gson();
//
//                    Log.d("backi","backu"+json);
//
//                    for (int i=0;i<jsonArray.length();i++)
//                    {
//                        json=jsonArray.getString(i);
//                        pandingGroup = gson.fromJson(json, PandingGroup.class);
//                        ITEMS.add(i,pandingGroup);
//                        Log.d("","jaani"+json);
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
//                progressDialog.cancel();
//            }
//        }) {
//            //adding parameters to send
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parameters = new HashMap<String, String>();
//                return parameters;
//            }
//        };
//
//        RequestQueue rQueue = Volley.newRequestQueue(context);
//        rQueue.add(request);
//    }
//}
