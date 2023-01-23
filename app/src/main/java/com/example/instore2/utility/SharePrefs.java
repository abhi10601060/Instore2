package com.example.instore2.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.instore2.models.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SharePrefs {

    public static String PREFERENCE = "Instore2";
    private SharedPreferences sharedPreferences;
    private static SharePrefs instance;
    public static String SESSIONID = "session_id";
    public static String USERID = "user_id";
    public static final String COOKIES = "Cookies";
    public static final String CSRF = "csrf";
    public static final String IS_INSTAGRAM_LOGIN = "Is_Instagram_Login";
    public static final String RECENT_SEARCHES = "Recent_search";

    public SharePrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
    }

    public static SharePrefs getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefs(context);
        }
        return instance;
    }

    public void putString(String str, String str2) {
        this.sharedPreferences.edit().putString(str, str2).apply();
    }

    public String getString(String str) {
        return this.sharedPreferences.getString(str, "");
    }

    public void putInt(String str, Integer num) {
        this.sharedPreferences.edit().putInt(str, num).apply();
    }

    public void putBoolean(String str, Boolean bool) {
        this.sharedPreferences.edit().putBoolean(str, bool).apply();
    }

    public Boolean getBoolean(String str) {
        return this.sharedPreferences.getBoolean(str, false);
    }

    public int getInt(String str) {
        return this.sharedPreferences.getInt(str, 0);
    }

    public void clearSharePrefs() {
        this.sharedPreferences.edit().clear().apply();
    }

    public void putRecentSearch(UserModel user){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ArrayList<UserModel>>(){}.getType();
        ArrayList<UserModel> recent_searches = gson.fromJson(this.sharedPreferences.getString(RECENT_SEARCHES , null) , type);

        if (recent_searches == null){
            Log.d("RECENT", "putRecentSearches Share: $ is null");
            ArrayList<UserModel> searches = new ArrayList<>();
            searches.add(user);
            this.sharedPreferences.edit().putString(RECENT_SEARCHES , gson.toJson(searches)).commit();
        }
        else{
            for (int i=0 ; i<recent_searches.size() ; i++){
                if (user.getPk() == recent_searches.get(i).getPk()){
                    recent_searches.remove(i);
                }
            }
            recent_searches.add(user);
            if (recent_searches.size() > 30){
                recent_searches.remove(0);
            }
            this.sharedPreferences.edit().remove(RECENT_SEARCHES).commit();
            this.sharedPreferences.edit().putString(RECENT_SEARCHES, gson.toJson(recent_searches)).commit();
        }
    }

    public ArrayList<UserModel> getRecentSearches(){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken< ArrayList<UserModel>>(){}.getType();
        ArrayList<UserModel> recent_searches = gson.fromJson(this.sharedPreferences.getString(RECENT_SEARCHES , null) , type);

        if (recent_searches != null){
            Log.d("RECENT", "getRecentSearches Share: " + recent_searches.toString());
            return recent_searches;
        }
        Log.d("RECENT", "getRecentSearches Share: $ is null");
        return new ArrayList<UserModel>();
    }


}
