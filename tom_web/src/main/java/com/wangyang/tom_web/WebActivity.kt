package com.wangyang.tom_web

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wangyang.common.WebViewConstant
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    private var webViewFragment: WebViewFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        webViewFragment =
            WebViewFragment.newInstance(intent.getStringExtra(WebViewConstant.WEB_URL) ?: "")
        supportFragmentManager.beginTransaction().replace(
                R.id.fm_web, webViewFragment!!
        ).commit()

        web_back.setOnClickListener {
          webViewFragment!!.back{
              finish()
          }
        }
    }

    fun updateTitle(title: String) {
        web_title.text = title
    }
}