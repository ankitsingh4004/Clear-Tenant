package com.example.admin.workorderlandlord.remote;

/**
 * Created by Bharat Tripathi on 02-May-18.
 */

public class Network {
    private static Network instance;
   private final ApiServicesInterface apiServices;


   private Network() {
        apiServices = RetrofitManager.getInstance().create(ApiServicesInterface.class);
    }

    public static Network getInstance() {
        if (instance==null){
            instance=new Network();
        }
        return instance;
    }

    public ApiServicesInterface getApiServices() {
        return apiServices;
    }

}
