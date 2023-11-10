package com.lighthouse.common_ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lighthouse.common_ui.R
import com.lighthouse.common_ui.util.UiState
import com.lighthouse.common_ui.util.showOKDialog
import com.lighthouse.common_ui.util.toast
import com.lighthouse.domain.constant.ErrorType
import com.lighthouse.domain.constant.TestException
import com.lighthouse.navigation.NavigationFlow
import com.lighthouse.navigation.ToFlowNavigatable

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
) : Fragment() {
    private var _binding: T? = null

    protected val binding: T
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected fun displayException(uiState: UiState.Error<*>) {
        val exception = uiState.message
        Log.d("TESTING", exception.toString())
        if (exception is TestException) {
            when (exception.errorType) {
                ErrorType.TOAST -> {
                    context.toast(exception.message.toString())
                }

                ErrorType.DIALOG -> {
                    showOKDialog(
                        requireContext(),
                        getString(R.string.error_title),
                        exception.message.toString()
                    )
                }


                ErrorType.DIRECT_AND_DIALOG -> {
                    showOKDialog(
                        requireContext(),
                        getString(R.string.error_title),
                        exception.message.toString(),
                        false,
                    ) { d, _ ->
                        d.dismiss()
                        (requireActivity() as ToFlowNavigatable).navigateToFlow(
                            NavigationFlow.SettingFlow(
                                ""
                            )
                        )
                    }
                }

                ErrorType.NONE -> {
                    // do nothing
                }
            }
        } else {
            context.toast(exception.toString())
        }
    }
}