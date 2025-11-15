package com.example.helloflow.ui.shareflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ShareFlowViewModel : ViewModel() {
    private val _events = MutableSharedFlow<String>(
        replay = 1 // 记住最近的 1条数据
    )
    val  events = _events.asSharedFlow()

    fun onButtonClick(): Unit {
        viewModelScope.launch {
            _events.emit("按钮被点击了！时间：${System.currentTimeMillis()}")
        }
    }


}