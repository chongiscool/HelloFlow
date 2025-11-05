package com.example.helloflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helloflow.ui.shareflow.ShareFlowFragment

class ShareFlowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_flow)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShareFlowFragment.newInstance())
                .commitNow()
        }
    }
}