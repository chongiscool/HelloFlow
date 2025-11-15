package com.example.helloflow.ui.shareflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ShareFlowViewModel : ViewModel() {
    private val _events = MutableSharedFlow<Int>()
    val  events = _events.asSharedFlow()

    fun onButtonClick(): Unit {
        viewModelScope.launch {
            _events.emit(1)
        }
    }


}