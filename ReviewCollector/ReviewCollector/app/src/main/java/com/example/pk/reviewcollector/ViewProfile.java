package com.example.pk.reviewcollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.Department;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.util.FTOdeletefile;
import com.example.pk.reviewcollector.util.FTPFileUpload;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewProfile extends AppCompatActivity {

    EditText username,fname,lname,email,password,repeatpassword,phone,depart;
    Button createaccount;
    de.hdodenhof.circleimageview.CircleImageView image;
    public static final List<Department> DEPARTMENTSLIST = new ArrayList<>();
    Button choose;
    int PICK_IMAGE_REQUEST = 111;
    Spinner department;
    Bitmap bitmap;
    Department departmentdata;
    SharedPreferences prefs;
    RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        department=(Spinner)findViewById(R.id.selectdepartment);

        ActionPerforme(getApplicationContext(), StaticData.servername+"GetAllDepartments","2");


        image=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.image);

        byte[] decodedString = Base64.decode(getimage(), Base64.DEFAULT);
//        Toast.makeText(getActivity().getApplicationContext(),getRememberimagebase64(), Toast.LENGTH_LONG).show();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);
        choose=(Button)findViewById(R.id.chose);
        male=(RadioButton)findViewById(R.id.male);
        female=(RadioButton)findViewById(R.id.felame);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                choose.setVisibility(View.INVISIBLE);
            }
        });
        username=(EditText)findViewById(R.id.username);
        username.setText(getusername());
        username.setEnabled(false);
        fname=(EditText)findViewById(R.id.fristname);

        final String name=getname();
        String[] namesp=name.split(" ");
        lname=(EditText)findViewById(R.id.lastname);
        fname.setText(namesp[0]);
        lname.setText(namesp[1]);
        email=(EditText)findViewById(R.id.email);
        email.setText(getemail());
        email.setEnabled(false);
        password=(EditText)findViewById(R.id.password);
        password.setText(getpass());
        repeatpassword=(EditText)findViewById(R.id.repeatpassword);
        repeatpassword.setText(getpass());
        phone=(EditText)findViewById(R.id.phone);

        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        createaccount=(Button)findViewById(R.id.createaccount);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(username.getText().toString().equals(""))
                {
                    username.setError("Please Enter Username");
                    Log.d("","1");

                }else
                if(fname.getText().toString().equals(""))
                {
                    fname.setError("Please Enter Firtsname");
                    Log.d("","2");

                }else
                if(lname.getText().toString().equals(""))
                {
                    lname.setError("Please Enter Lastname");
                    Log.d("","3");

                }else
                if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".com"))
                {
                    email.setError("Please Enter Valid Email");
                    Log.d("","4");

                }else
                if(password.getText().toString().equals(""))
                {
                    password.setError("Please Enter Password");
                    Log.d("","5");

                }else
                if(!repeatpassword.getText().toString().equals(password.getText().toString()))
                {
                    repeatpassword.setError("Password Not Match");
                    Log.d("","6");

                }else
                if(phone.getText().toString().equals("") || phone.getText().toString().length()<11)
                {
                    phone.setError("Please Enter Valid Phone No.");
                    Log.d("","7");

                }else
                if(department.getSelectedItemPosition()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Select Departrment",Toast.LENGTH_LONG).show();
                    Log.d("","8");

                }else
                {

                    Log.d("","9");

                    String gander;
                    if (male.isChecked())
                    {
                        gander="Male";

                    }else {
                        gander="Female";
                    }
                   String name=fname.getText().toString()+" "+lname.getText().toString();

                    String Url=StaticData.servername+"UpdateUser/"+name+"/"+email.getText().toString()+"/"+username.getText().toString() + typereal+"/"+username.getText().toString() +"/"+password.getText().toString()+"/Approved/"+gander+"/"+department.getSelectedItem().toString();
                    Url=Url.replace(" ","%20");
                    Log.d("",Url);
                    ActionPerforme(getApplicationContext(),Url,"10");


                }
            }
        });


    }
    public void CurrentUserDate(String email,String username, String password,String name ,String image,String usertype,String accountstatus ) {

        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("remember_email", email);
        editor.putString("remember_password", password);
        editor.putString("username", username);
        editor.putString("name", name);
        editor.putString("usertype",usertype);
        editor.putString("accountstatus",accountstatus);
        if (imagepath!=null)
        {
            editor.putString("image" , image);
            Log.d("change","change");
        }
        Log.d("change","change");




        editor.apply();
    }
    final List<String> list = new ArrayList<String>();
    public  void ActionPerforme(final Context context, final String URL , final String check)
    {



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                if (check.equals("10")) {


                    try {

                        JSONObject mainJson = new JSONObject(s);
                        json = mainJson.getString("IsAlreadyMemberResult");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (imagepath!=null) {
                        Bitmap src = BitmapFactory.decodeFile(imagepath);
                        CurrentUserDate(email.getText().toString(),username.getText().toString(),password.getText().toString(),fname.getText().toString()+" "+lname.getText().toString(),Base64.encodeToString(getBitmapAsByteArray(src), Base64.DEFAULT),"User","Approved");

                    }else
                    {
                        CurrentUserDate(email.getText().toString(),username.getText().toString(),password.getText().toString(),fname.getText().toString()+" "+lname.getText().toString(),"","User","Approved");

                    }

                    if (imagepath != null) {

                        FTOdeletefile deleteFile = new FTOdeletefile();
                        deleteFile.execute("/"+getusername()+".jpeg");
                        FTPFileUpload download = new FTPFileUpload(getusername() + typereal);
                        download.execute(imagepath);
                        Log.d("", "path" + imagepath);
                        Intent intent=new Intent(getApplicationContext(),new_feeds.class);
                        startActivity(intent);


                    }
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                    onBackPressed();



                } else if (check.equals("2")) {
                    list.add("Please Select Department");
                    JSONArray jsonArray = null;
                    Gson gson = new Gson();
                    JSONObject joete = null;
                    try {
                        joete = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DEPARTMENTSLIST.clear();
                    try {

                        jsonArray = joete.getJSONArray("GetAllDepartmentsResult");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                json = jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            departmentdata = gson.fromJson(json, Department.class);
                            DEPARTMENTSLIST.add(departmentdata);
                            Log.d("", "GetAllDepartmentsResult" + s);
                            list.add(departmentdata.getDepartmentName());

                        }
                    } catch (JSONException e) {
                        try {
                            json = joete.getString("GetAllDepartmentsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        departmentdata = gson.fromJson(json, Department.class);
                        DEPARTMENTSLIST.add(departmentdata);

                        Log.d("", "GetAllDepartmentsResult" + s);
                    }
                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>(context,
                            android.R.layout.simple_list_item_1, list);
                    adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    department.setAdapter(adp1);
                    for(int i=0;i<list.size();i++)
                    {
                        if (list.get(i).equals(getdepartment()))
                        {
                            department.setSelection(i);
                        }
                    }

                }else  if (check.equals("10"))
                {

                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Please Enter User Details", Toast.LENGTH_LONG).show();;
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
    String img_str="Fuck";

    String imagepath;
    String typereal=".jpeg";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                image.setImageBitmap(bitmap);
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte[] image=stream.toByteArray();
                System.out.println("byte array:"+image);

                Uri uri = data.getData();
                String uriString = uri.toString();
                String path = null;
                try {
                    path = getPath(getApplicationContext(), uri);
                    String type=getMimeType(path);


                    String[] strings=type.split("/");
                    typereal="."+strings[1];
                    Log.d("","Extention"+typereal);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                imagepath=path;

                img_str= Base64.encodeToString(image, 0);
                //Setting image to ImageView

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;}


    public String getusername() {
        prefs = this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    public String getimage() {
        prefs = this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("image", "");
    }

    public String getname() {
        prefs = this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("name", "");
    }

    public String getemail() {
        prefs = this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("remember_email", "");
    }
    public String getpass() {
        prefs = this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("remember_password", "");
    }

    public String getdepartment() {
        prefs = this.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("dep", "");
    }
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (typereal.contains("jpeg"))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        if (typereal.contains("png"))
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}



