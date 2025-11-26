package com.example.helloflow.ui.shareflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class UiEvent {
    object showLoading : UiEvent()
    object hideLoading : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
    object NavigationToDetail : UiEvent()
}

class ShareFlowViewModel : ViewModel() {
    private val _events = MutableSharedFlow<String>(
        replay = 1 // 记住最近的 1条数据
    )
    val  events = _events.asSharedFlow()

    private val _uiEvents = MutableSharedFlow<UiEvent>(
        replay = 0 // 通常不需要重复播放
    )
    val uiEvents = _uiEvents.asSharedFlow()

    fun onButtonClick(): Unit {
//        viewModelScope.launch {
//            _events.emit("按钮被点击了！时间：${System.currentTimeMillis()}")
//        }

        viewModelScope.launch {
            _uiEvents.emit(UiEvent.showLoading)
            delay(2000)
            _uiEvents.emit(UiEvent.hideLoading)
            delay(50)
            _uiEvents.emit(UiEvent.ShowToast("请求成功"))
        }
    }


}