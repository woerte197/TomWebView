package com.wangyang.usercenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener {
            UserCenterServiceImpl.login = true
            UserCenterServiceImpl.callbackAny?.invoke("wangyang")
        }
    }
}



