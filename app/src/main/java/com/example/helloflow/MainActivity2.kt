package com.example.helloflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helloflow.ui.stateflow.StateFlowFragment

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StateFlowFragment.newInstance())
                .commitNow()
        }
    }
}