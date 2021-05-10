package com.example.admin.workorderlandlord.remote;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bharat Tripathi on 02-May-18.
 */

public class PreferenceManager {
    private static final String NAME = "app";
    private static final String Key_User_Name= "usrname";
    private static final String Key_User_Role="userrole";
    private static final String Key_User_Id="userid";
    private static final String Key_Person_Company_Id="personcompanyid";
    private static final String Key_Parent_Company_Id="parentcompanyid";
    private static PreferenceManager preferenceManager;
    private static final String date_raised="dateraised";
    private  static final String Asset_ID="asset_Id";
    private final SharedPreferences mSharedPreferences;
    private PreferenceManager(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }
    public static PreferenceManager getInstance(Context context) {
        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager(context);
        }

        return preferenceManager;
    }
    public String getAsset_ID()
    {
        return  mSharedPreferences.getString(Asset_ID,"");
    }
    public void setAsset_ID(String assetId)
    {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(Asset_ID,assetId);
        editor.commit();
    }
    public String getDate_raised()
    {
        return mSharedPreferences.getString(date_raised,"");

    }
    public void setDate_raised(String date_raised)
    {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(date_raised,date_raised);
        editor.commit();
    }
    public String getKey_Parent_Company_Id() {
        return mSharedPreferences.getString(Key_Parent_Company_Id,"");
    }
    public void setKey_Parent_Company_Id(String parent_company_id)
    {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(Key_Parent_Company_Id,parent_company_id);
        editor.commit();
    }
    public String getKey_Person_Company_Id()
    {
        return mSharedPreferences.getString(Key_Person_Company_Id,"");
    }
    public void setKey_Person_Company_Id(String person_company_id)
    {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(Key_Person_Company_Id,person_company_id);
        editor.commit();
    }
    public String getKey_User_Name() {
        return mSharedPreferences.getString(Key_User_Name,"");
    }
    public String getKey_User_Role() {
        return mSharedPreferences.getString(Key_User_Role,"");
    }
    public String getKey_User_Id() {
        return mSharedPreferences.getString(Key_User_Id,"");
    }
    public void setKey_User_Name(String user_name)
    {
        SharedPreferences.Editor editor1 = mSharedPreferences.edit();
        editor1.putString(Key_User_Name,user_name);
        editor1.commit();
    }
    public void setKey_User_Id(String user_id)
    {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(Key_User_Id,user_id);
        editor.commit();
    }
    public void setKey_User_Role(String user_role)
    {
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(Key_User_Role,user_role);
        editor.commit();
    }
}
