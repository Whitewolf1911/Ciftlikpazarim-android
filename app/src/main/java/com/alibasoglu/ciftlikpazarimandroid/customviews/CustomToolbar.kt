package com.alibasoglu.ciftlikpazarimandroid.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.alibasoglu.ciftlikpazarimandroid.R
import com.alibasoglu.ciftlikpazarimandroid.core.fragment.ToolbarConfiguration
import com.alibasoglu.ciftlikpazarimandroid.databinding.CustomToolbarBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.viewbinding.viewBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = viewBinding(CustomToolbarBinding::inflate)

    fun configure(toolbarConfiguration: ToolbarConfiguration?) {
        if (toolbarConfiguration == null) {
            isVisible = false
            return
        }
        with(toolbarConfiguration) {
            initTitle(titleResId)
            configureStartButton(startIconResId, startIconClick)
        }
        initBackgroundColor(R.color.purple_500)
        isVisible = true
    }

    private fun initTitle(titleResId: Int?) {
        with(binding.toolbarTitleTextView) {
            isVisible = titleResId != null
            if (titleResId != null) setText(titleResId)
        }
    }

    private fun setTitle(title: String?) {
        with(binding.toolbarTitleTextView) {
            isVisible = title != null
            text = title.orEmpty()
        }
    }

    private fun configureStartButton(resId: Int?, clickAction: (() -> Unit)?) {
        binding.startImageButton.apply {
            if (resId == null) {
                isVisible = false
                return
            }
            setImageResource(resId)
            setOnClickListener { clickAction?.invoke() }
            isVisible = true
        }
    }

    private fun initBackgroundColor(resId: Int?) {
        resId?.let {
            binding.root.setBackgroundResource(resId)
        }
    }

}
