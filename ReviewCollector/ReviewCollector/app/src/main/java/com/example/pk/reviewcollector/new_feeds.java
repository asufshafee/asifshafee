package com.example.pk.reviewcollector;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Fragments.Profile;
import com.example.pk.reviewcollector.Fragments.groupFragment;
import com.example.pk.reviewcollector.Fragments.newfeedsFragment;
import com.example.pk.reviewcollector.Fragments.notificationFragment;
import com.example.pk.reviewcollector.Fragments.requestFragment;
import com.example.pk.reviewcollector.Fragments.usernewfeedFragment;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.Structure;
import com.example.pk.reviewcollector.Objects.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class new_feeds extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Toolbar toolbar;

    Intent intent;
    User user;
    SharedPreferences preferences;
    MyApplication app;
    Structure structure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feeds);

        preferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        intent =getIntent();
        user=new User();
        user.setName(getRemembername());
//        user.setPictureStream(getRememberimage());
        user.setUserType(getRememberusertype());
        app=new MyApplication(getApplication());
        structure=new Structure();

//        app.CurrentUserDate(user.getEmail(),user.getUsername(),user.getPassword(),user.getName(),user.getPictureStream().toString());



         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (user.getUserType().equals("admin"))
        {
            getMenuInflater().inflate(R.menu.menu_new_feeds, menu);
            return true;
        }else
        {

            return false;
        }


    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
    Button clear,save,adddepartment,btndepadddepartment,btnclear;
    EditText catagory,subcatagory,organization,txtdepartmant,txtdepartmentdescription;
    String depart="",departdescription="";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(new_feeds.this);
        progressDialog.setMessage("please wait...");
        if (id == R.id.definestructure) {
            depart="";
           ActionPerforme(progressDialog,getApplicationContext(),StaticData.servername+"GetStructure","GetStructureResult","1");

        }else  if (id==R.id.deletestructure)
        {
            ActionPerforme(progressDialog,getApplicationContext(),StaticData.servername+"GetStructure","GetStructureResult","7");

        }else if (id==R.id.modifystructure)
        {
            ActionPerforme(progressDialog,getApplicationContext(),StaticData.servername+"GetStructure","GetStructureResult","2");


        }else if(id==R.id.viewdelayedgroups)
        {
            Intent intent=new Intent(getApplicationContext(),ViewDelayedGroups.class);
            startActivity(intent);
        }
//        else if (id==R.id.viewreports)
//        {
//            Intent intent=new Intent(getApplicationContext(),Reports.class);
//            startActivity(intent);
//        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);

    }
    AlertDialog.Builder alertboxdep;





    EditText txtMOdifycatagoty,txtMOdifysubcatagory,txtMOdifyoriginization;
    Button btnmodifyadddepart,btnmodifyolddepart;
    public void modifystructure(final Structure structure)
    {

        final Dialog layout = new Dialog(new_feeds.this,android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.menu_modify_structure);
        txtMOdifycatagoty=(EditText)layout.findViewById(R.id.txtmodifycatagory);
        txtMOdifyoriginization=(EditText)layout.findViewById(R.id.txtmodifyoriginization);
        txtMOdifysubcatagory=(EditText)layout.findViewById(R.id.txtmodifysubcatagory);

        btnmodifyadddepart=(Button)layout.findViewById(R.id.btnmodifuadddepartment);
        btnmodifyolddepart=(Button)layout.findViewById(R.id.btnmodifold);

        txtMOdifysubcatagory.setText(structure.getSubCategory());
        txtMOdifycatagoty.setText(structure.getCategory());
        txtMOdifyoriginization.setText(structure.getOrganization());

        depart="";
        btnmodifyolddepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(new_feeds.this);
                String URL=StaticData.servername+Uri.encode("CreateStructure/"+txtMOdifyoriginization.getText().toString()+"/"+txtMOdifycatagoty.getText().toString()+"/"+txtMOdifysubcatagory.getText().toString());
                URL=URL.replace(" ","%20");
                ActionPerforme(progressDialog,getApplicationContext(),URL,"CreateStructureResult","3");
                layout.dismiss();

            }
        });
        btnmodifyadddepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (depart!="")
                {
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(new_feeds.this);
                    String URL=StaticData.servername+Uri.encode("CreateStructure/"+txtMOdifyoriginization.getText().toString()+"/"+txtMOdifycatagoty.getText().toString()+"/"+txtMOdifysubcatagory.getText().toString());
                    URL=URL.replace(" ","%20");
                    String result=ActionPerforme(progressDialog,getApplicationContext(),URL,"CreateStructureResult","4");

                }else
                {
                    showDepartmant(structure);
                }


            }
        });




layout.show();

    }
    Button deleteyes,deleteno;
    TextView txtdeleteMessage;

    public void deletestructure(String Message)
{

    final Dialog layout = new Dialog(new_feeds.this,android.R.style.Theme_Translucent_NoTitleBar);
    layout.setContentView(R.layout.menu_delete_structure);
    txtdeleteMessage=(TextView) layout.findViewById(R.id.message);
    deleteno=(Button)layout.findViewById(R.id.btndeleteno);
    deleteyes=(Button)layout.findViewById(R.id.btndeleteyes);

    txtdeleteMessage.setText(Message);
    deleteyes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(new_feeds.this);
            String URL=StaticData.servername+Uri.encode("DeleteStructure");
            String result=ActionPerforme(progressDialog,getApplicationContext(),URL,"CreateStructureResult","5");
            Log.d("u","bachu"+URL);
            layout.dismiss();

        }
    });

    deleteno.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layout.dismiss();

        }
    });

layout.show();
}

    public  void  definestructure()
    {


        final Dialog layout = new Dialog(new_feeds.this,android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.manu_define_structure);
        catagory=(EditText)layout.findViewById(R.id.txtcatagory);
        organization=(EditText)layout.findViewById(R.id.txtorganozation);
        subcatagory=(EditText)layout.findViewById(R.id.txtsubcatagory);

        clear=(Button)layout.findViewById(R.id.btnclear);
        save=(Button)layout.findViewById(R.id.btnsave);
        adddepartment=(Button)layout.findViewById(R.id.btnadddepartmant);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subcatagory.setText("");
                catagory.setText("");
                organization.setText("");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (organization.getText().equals(""))
                {
                    organization.setError("Please Enter Org.");
                }else if (catagory.getText().equals(""))

                {
                    catagory.setError("Please Select Category");
                }else  if (subcatagory.getText().equals(""))
                {
                    subcatagory.setError("please Enter Sub-Catagoty");
                }else
                {


                        ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(new_feeds.this);
                        String URL=StaticData.servername+Uri.encode("CreateStructure/"+organization.getText().toString()+"/"+catagory.getText().toString()+"/"+subcatagory.getText().toString());
                        URL=URL.replace(" ","%20");
                        String result=ActionPerforme(progressDialog,getApplicationContext(),URL,"CreateStructureResult","6");
                        Log.d("u","bachu"+URL);
                        layout.dismiss();


                }



            }
        });
        adddepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDepartmant(structure);
            }
        });
        layout.show();

    }
    SharedPreferences prefs;
    public void SaveDepartment(String name,String description ) {

        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("depname", name);
        editor.putString("dpdesc", description);


        editor.apply();
    }
    public String getDepartdescription() {
        preferences = this.getSharedPreferences("com.example.app", 0);
        return preferences.getString("dpdesc", "");
    }
    public String getDepart() {
        preferences = this.getSharedPreferences("com.example.app", 0);
        return preferences.getString("depname", "");
    }
    public void showDepartmant(Structure structure)
    {
        final Dialog layout = new Dialog(new_feeds.this,android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.menu_add_department);
        btnclear=(Button)layout.findViewById(R.id.btndepclear);
        btndepadddepartment=(Button)layout.findViewById(R.id.btndepadddepartment);
        txtdepartmant=(EditText)layout.findViewById(R.id.txtdepartmentname);
        txtdepartmentdescription=(EditText)layout.findViewById(R.id.txtdepartmentdescription);

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdepartmentdescription.setText("");
                txtdepartmant.setText("");

            }
        });
        btndepadddepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtdepartmant.getText().equals(""))
                {
                    txtdepartmant.setError("Please Fill");
                }else if (txtdepartmentdescription.getText().equals(""))
                {
                    txtdepartmentdescription.setError("Please Fill");
                }else {
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(new_feeds.this);
                    String URL=StaticData.servername+Uri.encode("AddDepartment/"+txtdepartmant.getText().toString()+"/"+txtdepartmentdescription.getText().toString());
                    URL=URL.replace(" ","%20");
                    String result=ActionPerforme(progressDialog,getApplicationContext(),URL,"CreateStructureResult","6");
                    Log.d("u","bachu"+URL);
                    layout.dismiss();
                }


            }
        });
        layout.show();


//         alertboxdep = new AlertDialog.Builder(mViewPager.getRootView().getContext());
//        alertboxdep.setMessage("ADD Department");
//        alertboxdep.setView(layout);
//
//        alertboxdep.setNeutralButton("OK",new DialogInterface.OnClickListener() {public void onClick(DialogInterface arg0, int arg1) {
////to do
//            Toast.makeText(getApplicationContext(),arg1+String.valueOf(arg0),Toast.LENGTH_LONG).show();
//        }});
//
//        alertboxdep.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_new_feeds, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if (user.getUserType().equals("admin"))
            {

                switch (position)
                {
                    case 0:

                        return  new newfeedsFragment();
                    case 1:
                        return  new requestFragment();
                    case 2:
                        return  new notificationFragment();
                    case 3:
                        return  new Profile();
                }
            }else
            {
                switch (position)
                {
                    case 0:
                        return  new usernewfeedFragment();
                    case 1:
                        return  new groupFragment();
                    case 2:
                        return  new notificationFragment();
                    case 3:
                        return  new Profile();
                }
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if (user.getUserType().equals("admin"))
            {
                switch (position) {
                    case 0:
                        return "New Feeds";
                    case 1:
                        return "Requests";
                    case 2:
                        return "Notification";
                    case 3:
                        return "Profile";
                }
            }else
            {

                switch (position) {
                    case 0:
                        return "New Feeds";
                    case 1:
                        return "Groups";
                    case 2:
                        return "Notification";
                    case 3:
                        return "Profile";
                }
            }

            return null;
        }
    }
    public String getRememberemail() {
        preferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("remember_email", "");
    }
    public String getRememberusername() {
        preferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }
    public String getRemembername() {
        preferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("name", "");
    }

    public String getRememberPassword() {
        preferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("remember_password", "");
    }
    public String getRememberimage() {
        preferences =this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("image", "");
    }
    public String getRememberusertype() {
        preferences =this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("usertype", "");
    }
    public String getRememberaccountstatus() {
        preferences =this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return preferences.getString("accountstatus", "");
    }




     String json;
    String objname;
    public  String ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL, String Objectname, final String check)
    {
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        objname=Objectname;

        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                 json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString(objname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (check.equals("1"))
                {
                    if (json!=null && json.contains("The Structure has not been created yet")) {
                        definestructure();
                    }else
                    {
                        definestructure();
                    }
                }else if (check.equals("2"))
                {
                    Gson gson = new Gson();

                    structure = gson.fromJson(json, Structure.class);
                    Log.d("backu","backu"+json);

                    if (json.contains("The Structure has not been created yet")) {
                        Toast.makeText(getApplicationContext(),"No Structure Found",Toast.LENGTH_LONG).show();

                    }else
                    {
                        modifystructure(structure);
                    }

                }else if (check.equals("6"))
                {
                    Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();

                }else if (check.equals("7"))
                {
                    Gson gson = new Gson();
                    structure=new Structure();
                    structure = gson.fromJson(json, Structure.class);
                    Log.d("backu","backu"+json);
                    if (json.contains("The Structure has not been created yet")) {
                        Toast.makeText(getApplicationContext(),"No Structure Found",Toast.LENGTH_LONG).show();

                    }else
                    {
                        deletestructure("Do you want to delete this structure?");

                    }
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
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);

        return json;

    }




}
