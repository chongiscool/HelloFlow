package com.example.helloflow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.helloflow.vm.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvShow = findViewById<TextView>(R.id.tv_show)
        val etInput = findViewById<EditText>(R.id.et_input)
        val btnSure = findViewById<Button>(R.id.btn_sure)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val btnStateFlow = findViewById<Button>(R.id.btn_state_flow)
        val btnSharedFlow = findViewById<Button>(R.id.btn_shared_flow)

        btnSharedFlow.setOnClickListener {
            val intent = Intent(this, ShareFlowActivity::class.java)
            startActivity(intent)
        }

        btnStateFlow.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }






//        lifecycleScope.launchWhenStarted {
//            viewModel.events.collect { msg ->
//                tvShow.text = msg
//            }
//        }

        fun append(s:String) {
            tvShow.text = "${tvShow.text.toString()}\n$s"
        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
////                delay(2000)
////                viewModel.events.collect { msg ->
////                    tvShow.text = msg
////                }
//
//                viewModel.events.collect {
////                    delay(300)
////                    append("A got: $it @${System.currentTimeMillis()%100000}")
//
//                    delay(200) // 慢！
//                    append("got: $it")
//                }
//
//            }
//        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
////                delay(500)
//
////                viewModel.events.collect {
////                    append("B got: $it @${System.currentTimeMillis()%100000}")
////                }
//            }
//        }

        btnSure.setOnClickListener {
//            viewModel.sendMsg(etInput.text.toString())
//            etInput.text.clear()

//            viewModel.burst()
        }

        btnCancel.setOnClickListener {
//            viewModel.sendMsg("tick @${System.currentTimeMillis()}")

            tvShow.text = ""
        }

    }

}

