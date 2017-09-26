package com.example.pk.reviewcollector;


        import android.app.Application;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;



public class MyApplication {

    private static MyApplication mInstance;
    public SharedPreferences preferences;
    public String prefName = "androidlivetv";
    Context context;

    public MyApplication( Context context) {
        context = context;
    }





    public void saveIsLogin(boolean flag) {
        preferences = context.getSharedPreferences(prefName, 0);
        Editor editor = preferences.edit();
        editor.putBoolean("IsLoggedIn", flag);
        editor.apply();
    }

    public boolean getIsLogin() {
        preferences = context.getSharedPreferences(prefName, 0);
        return preferences.getBoolean("IsLoggedIn", false);
    }



    public void CurrentUserDate(String email,String username, String password,String name ,String image) {

        preferences = context.getSharedPreferences(prefName, 0);
        Editor editor = preferences.edit();
        editor.putString("remember_email", email);
        editor.putString("remember_password", password);
        editor.putString("username", username);
        editor.putString("name", name);
        editor.putString("image",image);
        editor.apply();
    }

    public String getRememberemail() {
        preferences = context.getSharedPreferences(prefName, 0);
        return preferences.getString("remember_email", "");
    }
    public String getRememberusername() {
        preferences = context.getSharedPreferences(prefName, 0);
        return preferences.getString("username", "");
    }
    public String getRemembername() {
        preferences = context.getSharedPreferences(prefName, 0);
        return preferences.getString("name", "");
    }

    public String getRememberPassword() {
        preferences = context.getSharedPreferences(prefName, 0);
        return preferences.getString("remember_password", "");
    }
    public String getRememberimage() {
        preferences = context.getSharedPreferences(prefName, 0);
        return preferences.getString("image", "");
    }



}
