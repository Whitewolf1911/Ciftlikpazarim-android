package com.alibasoglu.ciftlikpazarimandroid.user.ui.updateinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.UserObject
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentChangeUserInfoBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.ProgressDialog
import com.alibasoglu.ciftlikpazarimandroid.utils.Resource
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.showToast
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChangeUserInfoFragment : BaseFragment(R.layout.fragment_change_user_info) {
    override val fragmentConfiguration = FragmentConfiguration()

    private val binding by viewBinding(FragmentChangeUserInfoBinding::bind)

    private val viewModel by viewModels<ChangeUserInfoViewModel>()

    var progressDialog: ProgressDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        progressDialog = activity?.let { ProgressDialog(activity = it) }
        with(binding) {
            with(UserObject) {
                nameEditText.setText(name)
                phoneEditText.setText(phoneNumber)
                emailEditText.setText(email)
            }

            submitButton.setOnClickListener {
                //TODO add validation
                viewModel.updateUserInfo(
                    name = nameEditText.text.toString(),
                    phoneNumber = phoneEditText.text.toString(),
                    email = emailEditText.text.toString()
                )
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.observe {
            viewModel.updateUserInfoState.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        progressDialog?.dismissDialog()
                        context?.showToast("Bilgileriniz güncellendi.")
                    }
                    is Resource.Error -> {
                        progressDialog?.dismissDialog()
                        context?.showToast(result.message ?: "İşlem Başarısız")
                    }
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            progressDialog?.startDialog()
                        }else{
                            progressDialog?.dismissDialog()
                        }
                    }
                }
            }
        }
    }
}
