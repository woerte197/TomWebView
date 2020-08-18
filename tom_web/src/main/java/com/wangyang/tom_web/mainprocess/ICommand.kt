package com.wangyang.tom_web.mainprocess

import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack

interface ICommand {
    fun execute(
        jsonParams: MutableMap<String, String>?,
        callBack: IMainProcessToWebViewProcessCallBack?
    )

    fun name(): String
}