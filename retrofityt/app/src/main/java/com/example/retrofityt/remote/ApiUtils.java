package com.example.retrofityt.remote;

public class ApiUtils {
    private ApiUtils(){

    }

    public static final String Api_Url ="https://192.168.10.1:8080/";
    public static UserService getUserService(){
        return RetrofitClient.getClient(Api_Url).create(UserService.class);
    }
}
