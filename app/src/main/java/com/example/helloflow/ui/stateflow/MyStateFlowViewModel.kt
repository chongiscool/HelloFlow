package com.example.helloflow.ui.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class TrafficLightState {
    data object Red: TrafficLightState()
    data object Yellow: TrafficLightState()
    data object Green: TrafficLightState()

    data object Blinking: TrafficLightState()
}

/**
 * 显示当前的状态
 */
sealed class UiState {
    data object Idle: UiState()
    data object Running: UiState()
    data object Finished: UiState()

}

/**
 * StateFlow vs LiveData quick notes:
 * 1. Lifecycle awareness: LiveData handles lifecycle automatically; StateFlow requires
 *    helpers such as repeatOnLifecycle/collectAsStateWithLifecycle.
 * 2. Threading model: LiveData is predominantly main-thread bound; StateFlow relies on
 *    coroutine dispatchers for flexible threading.
 * 3. API consistency: StateFlow integrates seamlessly with the rest of Flow
 *    operators (map/flatMapLatest/combine/distinctUntilChanged etc.).
 */
class MyStateFlowViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _trafficLightState = MutableStateFlow<TrafficLightState>(TrafficLightState.Red)
    val trafficLightState = _trafficLightState.asStateFlow()

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    private val _liveCount = MutableLiveData(0)
    val liveCount: LiveData<Int> = _liveCount

    fun next() {
        _trafficLightState.update { current ->
            when(current) {
                TrafficLightState.Green -> TrafficLightState.Yellow
                TrafficLightState.Yellow -> TrafficLightState.Blinking
                TrafficLightState.Red -> TrafficLightState.Green
                TrafficLightState.Blinking -> TrafficLightState.Red
            }
        }
    }

    fun increment() {
        _count.update { it + 1 }
    }

    fun reset() {
       _count.update { 0 }
    }

    fun incrementLiveCount() {
        val current = _liveCount.value ?: 0
        _liveCount.value = current + 1
    }

    fun resetLiveCount() {
        _liveCount.value = 0
    }

    fun startRunning() {
        _uiState.value = UiState.Running

        viewModelScope.launch {
            delay(2000L)
            _uiState.value = UiState.Finished
            delay(1000L)
            _uiState.value = UiState.Idle
        }

    }


}
