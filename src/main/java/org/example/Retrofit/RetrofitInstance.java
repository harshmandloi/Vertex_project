package org.example.Retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";


    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
