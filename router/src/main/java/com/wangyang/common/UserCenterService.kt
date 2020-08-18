package com.wangyang.common


interface UserCenterService {
    fun login(callback: (params:String) -> Unit)
    fun isLogin(): Boolean
}