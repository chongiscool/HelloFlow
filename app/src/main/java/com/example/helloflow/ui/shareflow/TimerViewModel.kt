package com.example.helloflow.ui.shareflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class TimerViewModel: ViewModel() {

    // cold flow: send value every 1s
    private val counterSource: Flow<Int> = flow {
        var i = 0
        while (i< 10) {
            emit(i++)
            delay(1000)
        }
    }

    // shareIn: share upstream in viewModelScope
    val counter: SharedFlow<Int> = counterSource.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        replay = 1 // new subscriber will get the last emitted value
    )
}