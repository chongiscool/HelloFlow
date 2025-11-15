package com.example.helloflow.ui.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    private val _liveCount = MutableLiveData(0)
    val liveCount: LiveData<Int> = _liveCount

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


}
