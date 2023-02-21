package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentChangePasswordBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.ProgressDialog
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.showToast
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment(R.layout.fragment_change_password) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentChangePasswordBinding::bind)

    private val viewModel by viewModels<ChangePasswordViewModel>()

    var progressDialog: ProgressDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        progressDialog = activity?.let { ProgressDialog(activity = it) }
        with(binding) {
            submitButton.setOnClickListener {
                //TODO if input valid
                viewModel.changePassword(newPassword = passwordEditText.text.toString())
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.changePasswordState.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        progressDialog?.dismissDialog()
                        context?.showToast("Şifreniz güncellendi.")
                    }
                    is Resource.Error -> {
                        progressDialog?.dismissDialog()
                        context?.showToast(result.message ?: "İşlem Başarısız")
                    }
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            progressDialog?.startDialog()
                        } else {
                            progressDialog?.dismissDialog()
                        }
                    }
                }
            }
        }
    }
}
