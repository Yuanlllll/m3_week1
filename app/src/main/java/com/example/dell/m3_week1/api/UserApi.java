package com.example.dell.m3_week1.api;

import com.example.dell.m3_week1.bean.ShowBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi
{
    @GET("meinv/?key=8322c06e5b327bbd19a026a643835f7e")
    Observable<ShowBean> getZs(@Query("page")int page,@Query("num")int num);

}
