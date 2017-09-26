package com.example.pk.reviewcollector.Objects;

/**
 * Created by jaani on 9/10/2017.
 */

public class UserSearch {

    String AccountStatus,Email,Format,ImagePath,Name,Password,UserType,Username;
    byte[] Base64Imag;

    public byte[] getBase64Imag() {
        return Base64Imag;
    }

    public void setBase64Imag(byte[] base64Imag) {
        Base64Imag = base64Imag;
    }

    public String getAccountStatus() {
        return AccountStatus;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public void setAccountStatus(String accountStatus) {
        AccountStatus = accountStatus;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
