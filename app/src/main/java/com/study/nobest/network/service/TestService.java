package com.study.nobest.network.service;

import com.study.nobest.mvp.model.entity.PoemListInfo;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public interface TestService {

        @GET("/index.php")
        Observable<String> getContent();

      //appType=1&operableSign=776814BF941B47E3B6776B2EE5A14724&versionCode=19
        @GET("checkVersion.action")
        Observable<Object> checkVersion(@Query("operableSign")String operableSign,@Query("appType")int appType,@Query("versionCode")int versionCode );


        //appType=1&pageId=1&userId=776814BF941B47E3B6776B2EE5A14724&poemType=2&operableSign=776814BF941B47E3B6776B2EE5A14724
        @GET("poemList.action")
        Observable<PoemListInfo> poemList(@Query("operableSign")String operableSign, @Query("userId")String userId, @Query("appType")int appType, @Query("poemType")int poemType, @Query("pageId")int pageId );


}
