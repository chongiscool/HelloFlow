package com.example.helloflow.ui.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CounterViewModel: ViewModel() {
    // 1. cold flow: +1 every 1s
    private val counterFlow: Flow<Int> = flow {
        var i = 0
        while(i < 10) {
            emit(i++)
            delay(1000L) // mock io operation

        }
    }

    // use `stateIn` to convert the cold flow to a hot stateflow
    val uiState: StateFlow<Int> = counterFlow.stateIn(
        scope = viewModelScope, // viewModel scope shareing ONLY
        started = SharingStarted.WhileSubscribed(5000), // subscribed only for running, stop after 5s later
        initialValue = 0 // initial value
    )



}