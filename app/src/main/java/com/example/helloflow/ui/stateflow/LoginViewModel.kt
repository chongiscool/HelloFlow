package com.example.helloflow.ui.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

// ----------------- 状态数据类 -----------------

// 定义 UI 状态：用 StateFlow 持有
data class LoginFormState(
    val usernameError:String? = null,
    val passwordError:String? = null,
    val isDataValid: Boolean = false,
    val isLoading: Boolean = false
)

// 定义一次性事件：用 SharedFlow 发送
sealed class LoginEvent {
    data object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}

@OptIn(FlowPreview::class)
class LoginViewModel: ViewModel() {
    // 1. StateFlow: 持有表单的当前状态
    private val _loginFormState = MutableStateFlow(LoginFormState())
    // 暴露给 UI (不可变)
    val loginFormState: StateFlow<LoginFormState> = _loginFormState

    // 2. SharedFlow: 发送一次性事件 (replay=0 表示默认不重播历史值)
    private val _loginEvent = MutableSharedFlow<LoginEvent>(extraBufferCapacity = 1)
    // 暴露给 UI (不可变)
    val loginEvent: SharedFlow<LoginEvent> = _loginEvent

    val username = MutableStateFlow("")

    init {
        viewModelScope.launch {
            username
                .debounce(500L)
                .collect { newUsername ->
                    println("Username is: $newUsername")
                }
        }
    }

    /**
     * 更新用户名输入状态
     */
    fun onUsernameChanged(username: String) {
        val newUsernameError = if (username.length < 5) "用户名至少5个字符" else null

        _loginFormState.value = _loginFormState.value.copy(
            usernameError = newUsernameError,
            // 重新计算数据是否有效
            isDataValid = (newUsernameError == null && _loginFormState.value.passwordError == null)
        )
    }

    /**
     * 模拟登录操作
     */
    fun login() {
        // 1. 更新 StateFlow: 状态变为加载中
        _loginFormState.value = _loginFormState.value.copy(isLoading = true)

        viewModelScope.launch {
            // 模拟网络请求
            kotlinx.coroutines.delay(2000)

            // 假设登录成功
            val success = true

            if (success) {
                // 2. 更新 StateFlow: 状态解除加载中
                _loginFormState.value = _loginFormState.value.copy(isLoading = false)

                // 3. 发送 SharedFlow: 发送登录成功事件 (一次性信号)
                _loginEvent.emit(LoginEvent.Success)

            } else {
                // 4. 发送 SharedFlow: 发送错误事件 (一次性信号)
                _loginEvent.emit(LoginEvent.Error("登录失败，请重试"))
                // 5. 更新 StateFlow: 状态解除加载中
                _loginFormState.value = _loginFormState.value.copy(isLoading = false)
            }
        }
    }

}


