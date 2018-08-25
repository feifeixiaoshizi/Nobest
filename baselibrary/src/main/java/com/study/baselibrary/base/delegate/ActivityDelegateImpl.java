/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.study.baselibrary.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * ================================================
 * {@link ActivityDelegate} 默认实现类
 * <p>
 * Created by JessYan on 26/04/2017 20:23
 * Contact with <mailto:jess.yan.effort@gmail.com>
 * Follow me on <https://github.com/JessYanCoding>
 * ================================================
 * 主要负责为ACtivity提供AppComponent对象。
 * 在Appllication中监听到Activity的onActivityCreated会创建一个Activity的代理对象，并调用它的
 * onCreate（）方法。
 *
 *
 * 代理Activity封装了真实的Activity对象，然后为真实的Activity做些操作。（*****）
 *
 *
 * 1：为ActivitysetupActivityComponent（）的方法提供参数AppComponent对象。
 * 2：调用真实Activity的setupActivityComponent（）方法。
 *
 *
 *
 */
public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityDelegateImpl> CREATOR = new Parcelable.Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };
}
