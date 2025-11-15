package com.example.helloflow.ui.stateflow

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import com.example.helloflow.R
import com.example.helloflow.ui.showLongToast
import com.example.helloflow.ui.showShortToast
import kotlinx.coroutines.launch

class StateFlowFragment : Fragment() {

    companion object {
        fun newInstance() = StateFlowFragment()
    }


    private val viewModel: MyStateFlowViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_state_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShow = requireView().findViewById<TextView>(R.id.tv_msg)
        val tvTrafficLight = requireView().findViewById<TextView>(R.id.tv_light)
        val btnConfirm = requireView().findViewById<Button>(R.id.btn_confirm)
        val btnCancel = requireView().findViewById<Button>(R.id.btn_cancel)
        val btnChangeLight = requireView().findViewById<Button>(R.id.btn_change_light)
        val progressBar = requireView().findViewById<ProgressBar>(R.id.progressBar)
        val etUsername = requireView().findViewById<EditText>(R.id.et_username)
        val btnLogin = requireView().findViewById<Button>(R.id.btn_login)
        val btnStartRunning = requireView().findViewById<Button>(R.id.btn_start_running)

        // 触发登录
        btnConfirm.setOnClickListener {
//            viewModel.increment()
            viewModel.incrementLiveCount()
        }

        // 重置
        btnCancel.setOnClickListener {
//            viewModel.reset()
            viewModel.resetLiveCount()
        }

        // 换灯
        btnChangeLight.setOnClickListener {
            viewModel.next()
        }

        btnStartRunning.setOnClickListener {
            viewModel.startRunning()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    tvShow.text = when(uiState) {
                        is UiState.Idle -> "当前：空闲"
                        is UiState.Running -> "当前：运行中"
                        is UiState.Finished -> "当前：已完成"
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trafficLightState.collect { lightState ->
                    tvTrafficLight.text = when(lightState) {
                        TrafficLightState.Red -> "红灯"
                        TrafficLightState.Yellow -> "黄灯"
                        TrafficLightState.Green -> "绿灯"
                        TrafficLightState.Blinking -> "黄灯(闪烁)"
                    }
                }

            }
        }

        // ------------------ 收集 StateFlow (持续状态) ------------------
        // StateFlow 收集示例：更新 UI 状态 (如错误提示、按钮状态)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginFormState.collect { state ->
                    // 根据 state 更新 UI：
                    // 1. 设置用户名错误提示 (StateFlow 持有当前值)
                    etUsername.error = state.usernameError
                    // 2. 控制登录按钮是否可点击
                    btnLogin.isEnabled = state.isDataValid && !state.isLoading
                    // 3. 控制进度条可见性
                    progressBar.isVisible = state.isLoading
                }
            }
        }

        // ------------------ 收集 SharedFlow (一次性事件) ------------------
        // SharedFlow 收集示例：触发 Toast、导航等一次性行为
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 这里的 collect 只有在 STARTED 状态才运行
                loginViewModel.loginEvent.collect { event ->
                    when (event) {
                        is LoginEvent.Success -> {
                            // 登录成功，执行导航操作，**只执行一次**
                            showShortToast("登录成功！")
                        }

                        is LoginEvent.Error -> {
                            // 登录失败，显示 SnackBar，**只显示一次**
                            showLongToast("登录失败!")
                        }
                    }
                }
            }
        }

        // 当 et 文本有变化时，就会自动将变化后的 text 传递给下方
        etUsername.doOnTextChanged { text, _, _, _ ->
            loginViewModel.onUsernameChanged(text.toString())
        }

        // 使用携程来收集
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.count.collect { tvShow.text = it.toString() }
//            }
//        }

        // LiveData 本身具备生命周期感知能力，这里直接观察即可。
        viewModel.liveCount.observe(viewLifecycleOwner) { count ->
            tvShow.text = count.toString()
        }

        btnLogin.setOnClickListener {
            loginViewModel.login()
        }


    }

}
