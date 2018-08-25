/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.study.baselibrary.network;

import android.app.Application;
import android.content.Context;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import retrofit2.Retrofit;

/**
 * 关联一个Retrofit，负责该Retrofit下的所有的Service。
 * */
@Singleton
public class RetrofitServiceManager {
    //默认缓存大小
    private static int defaultCapacity = 10;

    //设置LRU缓存队列，默认缓存的大小
    private final LinkedHashMap<Class, Object> services = new LinkedHashMap<Class, Object>(defaultCapacity, 0.75f, true);


    private Retrofit mRetrofit;

    @Inject
    public RetrofitServiceManager(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }


    /*每次重置Retrofit都要清空原来的缓存*/
    public RetrofitServiceManager resetRetrofit(Retrofit retrofit) {
        this.mRetrofit = retrofit;
        clear();
        return this;
    }


    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     * @param service
     * @param <T>
     * @return
     */
    public <T> T obtainRetrofitService(Class<T> service) {
        T retrofitService;
        synchronized (services) {
            retrofitService = (T) services.get(service);
            if (retrofitService == null) {
                retrofitService = mRetrofit.create(service);
                services.put(service, retrofitService);
            }
        }
        return retrofitService;
    }

    public synchronized void clear() {
         services.clear();

    }


}
