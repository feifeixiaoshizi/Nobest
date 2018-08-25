package com.study.baselibrary.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/2/28 0028.
 *
 *
 *     /*

 //日志
 Log.d(TAG,"response:"+response.toString());
 Log.d(TAG,"networkResponse:"+response.networkResponse().toString());
 Log.d(TAG,"body:"+response.body().toString());
 Log.d(TAG,"bodyString:"+response.body().string());
 response.body().string()//方法只能调用一次，之后就会关闭流。所以后续再使用就得重新创建。

 response:Response{protocol=http/1.1, code=200, message=OK,
 url=http://17moyan.me:9090/MOYAN_BMS/frontUser/poemList.action?operableSign=776814BF941B47E3B6776B2EE5A14724&userId=&appType=1&poemType=2&pageId=1}

 networkResponse:Response{protocol=http/1.1,
 code=200, message=OK, url=http://17moyan.me:9090/MOYAN_BMS/frontUser/poemList.action?operableSign=776814BF941B47E3B6776B2EE5A14724&userId=&appType=1&poemType=2&pageId=1}

 body:okhttp3.internal.http.RealResponseBody@3252ec*/



public class ResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        Response response =  chain.proceed(request);
        response = filterResponse(response);
        return response;

    }

    /**
     * 对响应的结果进行判断，如果是正确的结果则过滤掉一些额外的内容，再进行返回，如果是错误的直接抛出异常交给使用端去处理。
     * */
    private Response filterResponse(Response response) throws IOException{
        NetResponse netResponse = null;
        String responseBodyString =null;
        try {
            //仅仅可以调用一次（***）
            responseBodyString =  response.body().string();
            netResponse = JSON.parseObject(responseBodyString,NetResponse.class);
            //没有错误
            if(!netResponse.isHasError()){
                ResponseBody responseBody = ResponseBody.create(response.body().contentType(),netResponse.getResult());
                return  response.newBuilder().body(responseBody).build();
            }else{
                //抛出异常，交给调用方去处理，把请求正确的，但是返回数据不正确的情况也定义为异常来处理。
                CustomNetwokException customNetwokException = new CustomNetwokException(netResponse.getErrorType(),netResponse.getErrorMessage());
                throw customNetwokException;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException jsonException){
            jsonException.printStackTrace();
        }
        //如果不是NetResponse格式的数据就直接再返回，不做任何处理操作。
        ResponseBody responseBody = ResponseBody.create(response.body().contentType(),responseBodyString);
        return  response.newBuilder().body(responseBody).build();

    }
}
