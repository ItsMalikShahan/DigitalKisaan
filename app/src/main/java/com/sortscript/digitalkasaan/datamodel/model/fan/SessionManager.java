package com.sortscript.digitalkasaan.datamodel.model.fan;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SessionManager {

    SharedPreferences preferences;
    Context context;
    public static String APP_NAME = "Digital Kasaan";
    final String IS_LOGIN = "Digital Kasaan";
    final String EMAIL = "EMAIL";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(APP_NAME, context.MODE_PRIVATE);
    }

    public void setLogin(Boolean isLoggedin) {
        preferences.edit().putBoolean(IS_LOGIN, isLoggedin).apply();
    }

    public Boolean getLogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void setObject(UserModel userModel) {
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        preferences.edit().putString("userModel", json).apply();
    }

    public UserModel getobject() {
        Gson gson = new Gson();
        String json = preferences.getString("userModel", "");
        UserModel obj = gson.fromJson(json, UserModel.class);
        return obj;
    }
    public String getLanguages() {
        return preferences.getString("languages", "");
    }

    public void setLanguages(String languages) {
        preferences.edit().putString("languages", languages).apply();
    }

}
