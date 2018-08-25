package com.study.baselibrary.network;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/2/27 0027.
 * retrofit支持多个转化器，但是在Retrofit内部如何选择正确的转换器是开发者应该把握好的，不然就会取到错误的转换器导致出错。
 */

public class StringConverterFactory extends Converter.Factory {
    private String TAG = "StringConverterFactory";
    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    //通过这个方法提供一个解析返回数据的转换器（来源 和 目标进行双重判断是否可以完成这次转换）
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Log.d(TAG,"type:"+type.toString());
        //如果响应类型type是String类型，则可以使用该转换器进行转换，否则就表示该转换器不能用就直接返回为null。（****）
        //通过type判断自己是否有能力进行对响应结果进行转换。（******）
        //也可以判断ResponseBody是否是你可以解析的，比如在Gson中返回的xml的数据格式就不行了。
        if(type instanceof Class&&String.class==type){
            return new StringConverter();
        }
        return  null;

    }


    final class StringConverter implements Converter<ResponseBody, String> {
        @Override
        public String convert(ResponseBody value) throws IOException {
            BufferedReader r = new BufferedReader(new InputStreamReader(value.byteStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        }
    }

}
