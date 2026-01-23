package com.tbc.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<STATE, EFFECT, EVENT>(
    initialState: STATE,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<EFFECT>() }
    val sideEffect = _sideEffect.receiveAsFlow()

    open fun onEvent(event: EVENT) = Unit

    protected fun updateState(block: STATE.() -> STATE) {
        _state.update(block)
    }

    protected fun emitSideEffect(sideEffect: EFFECT) {
        viewModelScope.launch {
            _sideEffect.send(sideEffect)
        }
    }
}