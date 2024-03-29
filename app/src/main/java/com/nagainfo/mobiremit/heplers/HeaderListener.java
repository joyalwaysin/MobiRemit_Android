/*
 * Copyright (c) 2017.  Joe
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nagainfo.mobiremit.heplers;


public interface HeaderListener {


    void onRefreshBefore(int scrollY, int headerHeight);


    void onRefreshAfter(int scrollY, int headerHeight);


    void onRefreshReady(int scrollY, int headerHeight);


    void onRefreshing(int scrollY, int headerHeight);

    void onRefreshComplete(int scrollY, int headerHeight, boolean isRefreshSuccess);


    void onRefreshCancel(int scrollY, int headerHeight);

    int getRefreshHeight();
}
