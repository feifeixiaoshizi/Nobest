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

import android.app.Activity;
import android.content.Intent;

/**
 *
 * 仅仅封装View的动作，不关心View的具体实现细节。
 * 方法都是无参数无返回值的。
 *
 *
 */
public interface IView {

    /**
     * 杀死自己
     */
    void destroy();
}
