package com.alibasoglu.ciftlikpazarimandroid.core.fragment

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ToolbarConfiguration(
    @StringRes
    val titleResId: Int? = null,
    @DrawableRes
    val startIconResId: Int? = null,
    val startIconClick: (() -> Unit)? = null
)
