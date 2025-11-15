package com.example.helloflow.ui.shareflow

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import com.example.helloflow.R
import kotlinx.coroutines.launch

class ShareFlowFragment : Fragment() {

    companion object {
        fun newInstance() = ShareFlowFragment()
    }

    private val viewModel: ShareFlowViewModel by viewModels()

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

        val tvShow = view.findViewById<TextView>(R.id.tv_msg)
        val btnClear = view.findViewById<TextView>(R.id.btn_clear)
        val btnConfirm = view.findViewById<TextView>(R.id.btn_confirm)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    // make a toast to show event value
                    Toast.makeText(requireContext(), event.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnClear.setOnClickListener {
            tvShow.text = ""
        }

        btnConfirm.setOnClickListener {
            viewModel.onButtonClick()
        }




    }

}