package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.BaseFragment
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.FragmentConfiguration
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.FragmentCategoryAdvertsBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.lifecycle.observe
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CategoryAdvertsFragment : BaseFragment(R.layout.fragment_category_adverts) {

    private val toolbarConfiguration = ToolbarConfiguration(
        startIconResId = R.drawable.ic_baseline_arrow_back_24,
        startIconClick = ::navBack,
    )
    override val fragmentConfiguration = FragmentConfiguration(toolbarConfiguration = toolbarConfiguration)

    private val categoryAdvertsViewModel by viewModels<CategoryAdvertsViewModel>()

    private val categoryAdvertsAdapter = CategoryAdvertsAdapter()

    private val binding by viewBinding(FragmentCategoryAdvertsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        getCategoryAdverts()
    }


    private fun initUi() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.isVisible = false
        getToolbar()?.setTitle(categoryAdvertsViewModel.getCategoryName())
        with(binding) {
            advertsRecyclerView.adapter = categoryAdvertsAdapter
            val listener = object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {
                    emptyStatusTextView.visibility = View.GONE
                }

                override fun onChildViewDetachedFromWindow(view: View) {}
            }
            advertsRecyclerView.addOnChildAttachStateChangeListener(listener)
        }
    }

    private fun getCategoryAdverts() {
        viewLifecycleOwner.observe {
            categoryAdvertsViewModel.getCategoryAdverts().collectLatest {
                categoryAdvertsAdapter.submitData(it)
            }
        }
    }

}
