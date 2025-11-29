package com.example.helloflow.ui.shareflow

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import com.example.helloflow.R
import com.example.helloflow.ui.showShortToast
import kotlinx.coroutines.launch

class ShareFlowFragment : Fragment() {

    companion object {
        fun newInstance() = ShareFlowFragment()
        const val TAG = "ShareFlowFragment"
    }

    private val viewModel: ShareFlowViewModel by viewModels()
    private val timerViewModel: TimerViewModel by viewModels()

    private lateinit var tvShow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_share_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShow = view.findViewById<TextView>(R.id.tv_msg)
        val tvCounter = view.findViewById<TextView>(R.id.tv_counter)
        val btnClear = view.findViewById<TextView>(R.id.btn_clear)
        val btnConfirm = view.findViewById<TextView>(R.id.btn_confirm)
        val pbLoading = view.findViewById<ProgressBar>(R.id.pb_shared)

        collectMultiSubscriptions()

        btnClear.setOnClickListener {
            tvShow.text = ""
        }

        btnConfirm.setOnClickListener {
            viewModel.onButtonClick()
        }

        collectUiEvent(progressBar = pbLoading)

        collectCounter(tvCounter)

    }

    private fun showMsg(x: Any) {
        tvShow.text = "收到🫡：$x"
    }

    fun collectMultiSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.events.collect { event ->
                        // make a toast to show event value
                        showShortToast(event.toString())
                    }
                }

                launch {
                    viewModel.events.collect { event ->
                        showMsg(event.toString())
                    }
                }

                launch {
                    viewModel.events.collect { event ->
                        Log.d(TAG, "event: $event")
                    }
                }

            }

        }
    }

    fun collectCounter(tvCounter: TextView) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    timerViewModel.counter.collect { counter ->
                        tvCounter.text = counter.toString()
                    }
                }

                launch {
                    timerViewModel.counter.collect { counter ->
                        Log.d(TAG, "counter: $counter")
                    }
                }
            }
        }
    }

    fun collectUiEvent(progressBar: ProgressBar): Unit {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvents.collect { event ->
                    when(event) {
                        is UiEvent.showLoading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is UiEvent.hideLoading -> {
                            progressBar.visibility = View.GONE
                        }
                        is UiEvent.ShowToast -> {
                            showShortToast(event.message)
                        }
                        else -> {
                            showShortToast("NavigationToDetailPage: Not implement yet")
                        }
                    }
                }
            }
        }
    }

}