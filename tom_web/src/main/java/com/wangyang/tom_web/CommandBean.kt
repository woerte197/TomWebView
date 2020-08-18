package com.wangyang.tom_web

import com.google.gson.Gson

class CommandBean {
    val cmd: String? = null
    val message: MutableMap<String, String>? = null

    fun execute(values: String): CommandBean {
        return Gson().fromJson(values, CommandBean::class.java)
    }
}