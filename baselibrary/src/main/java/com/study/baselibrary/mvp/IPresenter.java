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
package com.study.baselibrary.mvp;


/**
 * 封装了开始和结束两个方法，不需要参数不需要返回值。
 * 因为Presenter可以直接和View和Model交互，Presenter的职责主要是控制
 * 一些特定时机的操作，而不必关心具体的细节参数。
 *
 */
public interface IPresenter<V extends IView> {

    void start();


    void destroy();


    void setView(V view);
}
