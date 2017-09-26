package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.Objects.UserSearch;
import com.example.pk.reviewcollector.util.FTPFileDownload;
import com.example.pk.reviewcollector.util.FTPFileUpload;
import com.example.pk.reviewcollector.util.WCFHandler;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Create_Account extends AppCompatActivity {

    EditText username,fname,lname,email,password,repeatpassword,phone,depart;
    Button createaccount;
    de.hdodenhof.circleimageview.CircleImageView image;
    public static final List<Department> DEPARTMENTSLIST = new ArrayList<>();
    Button choose;
    int PICK_IMAGE_REQUEST = 111;
    Spinner department;
    Bitmap bitmap;
    Department departmentdata;
    RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__account);

        department=(Spinner)findViewById(R.id.selectdepartment);
        ActionPerforme(getApplicationContext(),StaticData.servername+"GetAllDepartments","2");


        image=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.image);
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
        fname=(EditText)findViewById(R.id.fristname);
        lname=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        repeatpassword=(EditText)findViewById(R.id.repeatpassword);
        phone=(EditText)findViewById(R.id.phone);

        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        createaccount=(Button)findViewById(R.id.createaccount);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals(""))
                {
                    username.setError("Please Enter Username");

                }else
                if(fname.getText().toString().equals(""))
                {
                    fname.setError("Please Enter Firtsname");

                }else
                if(lname.getText().toString().equals(""))
                {
                    lname.setError("Please Enter Lastname");

                }else
                if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".com"))
                {
                    email.setError("Please Enter Valid Email");

                }else
                if(password.getText().toString().equals(""))
                {
                    password.setError("Please Enter Password");

                }else
                if(!repeatpassword.getText().toString().equals(password.getText().toString()))
                {
                    repeatpassword.setError("Password Not Match");

                }else
                if(phone.getText().toString().equals("") || phone.getText().toString().length()<11)
                {
                    phone.setError("Please Enter Valid Phone No.");

                }else
                if(department.getSelectedItemPosition()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Select Departrment",Toast.LENGTH_LONG).show();

                }else
                {

//                    WCFHandler wcf=new WCFHandler();
//                    String result = null;
//                    File file=new File(imagepath);
//
//                    try {
//                        result = wcf.upload("/abc.jpg", "Anonymous", "Anonymous", "abc" + ".jpg", file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();


                    ActionPerforme(getApplicationContext(),StaticData.servername+"IsAlreadyMember/"+username.getText().toString()+"/"+email.getText().toString(),"1");


                }
            }
        });



    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        if (typereal.contains("jpeg"))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
//        if (typereal.contains("png"))
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    final List<String> list = new ArrayList<String>();
    public  void ActionPerforme(final Context context, final String URL , final String check)
    {



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                if (check.equals("1")) {


                    try {

                        JSONObject mainJson = new JSONObject(s);
                        json = mainJson.getString("IsAlreadyMemberResult");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (json.contains("Success")) {
                        if (imagepath != null) {
                            FTPFileUpload download = new FTPFileUpload(username.getText().toString() + typereal);
                            download.execute(imagepath);
                            Log.d("", "path" + imagepath);


                            String gander;
                            if (male.isChecked())
                            {
                                gander="Male";

                            }else {
                                gander="Female";
                            }

                            Bundle userdate = new Bundle();
                            userdate.putString("name", fname.getText().toString() + " " + lname.getText().toString());
                            userdate.putString("password", password.getText().toString());
                            userdate.putString("username", username.getText().toString());
                            userdate.putString("email", email.getText().toString());
                            userdate.putString("phone", phone.getText().toString());
                            userdate.putString("path",username.getText().toString() + typereal);
                            userdate.putString("formet", typereal);
                            userdate.putString("gander",gander);
//                            Bitmap src= BitmapFactory.decodeFile(imagepath);
                            Bitmap bm=((BitmapDrawable)image.getDrawable()).getBitmap();
                            userdate.putString("image",Base64.encodeToString(getBitmapAsByteArray(bm), Base64.NO_WRAP));

                            userdate.putString("department",list.get(department.getSelectedItemPosition()));


                            Intent intent = new Intent(getApplicationContext(), Email_Varification.class);
                            intent.putExtra("userdata", userdate);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Select Profile Picture", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username or email already exist", Toast.LENGTH_LONG).show();
                    }


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



}
