// IWebViewProcessToMainProcesasInterface.aidl
package com.wangyang.tom_web;
import java.util.Map;
// Declare any non-default types here with import statements
import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack;

interface IWebViewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
  void handleWebCommand( String commandName,in Map<String,String> jsonParams,in IMainProcessToWebViewProcessCallBack callBack);
}
