package com.example.egarden;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor ;
    private static SharedPreference mysharedPreferance=null;

    private SharedPreference(Context context)
    {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    public static synchronized SharedPreference getPreferences(Context context)
    {

        if(mysharedPreferance==null) {
            mysharedPreferance = new SharedPreference(context);
        }

        return mysharedPreferance;
    }


    public void setEmail(String email)
    {
        editor.putString("email",email);
        editor.apply();
    }

    public String getEmail()
    {
        return sharedPreferences.getString("email","none");
    }

    public void setName(String name)
    {
        editor.putString("name",name);
        editor.apply();
    }

    public String getName()
    {
        return sharedPreferences.getString("name","none");
    }


    public void setImage(String image)
    {
        editor.putString("image",image);
        editor.apply();
    }

    public String getImage()
    {
        return sharedPreferences.getString("image","none");
    }


    public void setPump(String pump)
    {
        editor.putString("pump",pump);
        editor.apply();
    }

    public String getPump()
    {
        return sharedPreferences.getString("pump","none");
    }


    public void setAutoWatering(String auto)
    {
        editor.putString("auto",auto);
        editor.apply();
    }

    public String getAutoWatering()
    {
        return sharedPreferences.getString("auto","none");
    }

}
