package org.example.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JsonPlaceholderApi {
    @GET("/posts")
    Call<List<Post>> getPost();

}
