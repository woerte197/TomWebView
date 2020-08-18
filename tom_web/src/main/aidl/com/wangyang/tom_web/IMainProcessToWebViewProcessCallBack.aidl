// IMainProcessToWebViewProcessCallBack.aidl
package com.wangyang.tom_web;

// Declare any non-default types here with import statements

interface IMainProcessToWebViewProcessCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
 void onResult(String callBackName,String response);
}
