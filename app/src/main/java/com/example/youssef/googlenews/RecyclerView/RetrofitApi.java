package com.example.youssef.googlenews.RecyclerView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("v2/everything?q=bitcoin&from=2018-09-04&sortBy=publishedAt&apiKey=3d65a9425631415e811eb0ded7a3e20b")
    Call<NewsModel> getNews();
}
