package com.wangyang.tom_web.mainprocess

import com.wangyang.tom_web.IMainProcessToWebViewProcessCallBack
import com.wangyang.tom_web.IWebViewProcessToMainProcessInterface
import java.util.*
import kotlin.collections.ArrayList

class MainProcessCommandManager : IWebViewProcessToMainProcessInterface.Stub() {
    var list: ArrayList<ICommand> = ArrayList()

    companion object {
        val ins by lazy {
            MainProcessCommandManager()
        }
    }

    init {
        ServiceLoader.load(ICommand::class.java).iterator().forEach {
            list.add(it)
        }
    }

    override fun handleWebCommand(
        commandName: String?,
        jsonParams: MutableMap<String, String>?,
        callBack: IMainProcessToWebViewProcessCallBack?
    ) {
        list.forEach {
            if (it.name() == commandName) {
                it.execute(jsonParams,callBack)
            }
        }
    }

}