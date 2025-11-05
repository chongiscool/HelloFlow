package com.example.helloflow.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _events = MutableSharedFlow<String>(
        replay = 0,
//        extraBufferCapacity = 0,
//        extraBufferCapacity = 8,
        extraBufferCapacity = 4, // 小点，便于观察丢弃
//        onBufferOverflow = BufferOverflow.SUSPEND
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
//    val events = _events.asSharedFlow()
    val events: SharedFlow<String> = _events

    private val _state = MutableStateFlow<String>("")
    val state: StateFlow<String> = _state

    init {
        viewModelScope.launch {
//            _events.emit("1st event: A")
//            delay(1000)
//            _events.emit("2nd event: B")
//            delay(1000)
//            _events.emit("2nd event: C")
        }

    }

    fun burst() = viewModelScope.launch {
        // 快速连发 10 个
//        repeat(10) { i ->
//            _events.emit("$i")           // 可能会被挂起
//            // 不加 delay：更容易观察被“慢订阅者”背压

//            val ok = _events.tryEmit("$i")
//            if (!ok) {
//                // 缓冲爆满：再用挂起的 emit 顶一下（或直接丢弃/记录）
//                _events.emit("$i")
//            }
//        }

//        repeat(15) { i ->
//            _events.tryEmit("$i") // 从不挂起；缓冲满就丢最老的
//            // 也可以用 emit，但此处为了保证“从不阻塞”，用 tryEmit
//        }

        repeat(100) { i ->
            delay(10)
            _events.tryEmit("$i")
        }
    }

    fun sendMsg(msg: String) {
        viewModelScope.launch {
            _events.emit(msg) // emit an event
        }
    }

    /**
     * Try to send an event. False is return if there is no subscribers.
     */
    fun trySendMsg(msg: String) = _events.tryEmit(msg)
}