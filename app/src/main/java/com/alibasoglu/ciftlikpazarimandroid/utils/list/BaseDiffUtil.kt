package com.alibasoglu.ciftlikpazarimandroid.utils.list

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T : RecyclerListItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem areItemsTheSame newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem areContentsTheSame newItem
    }
}
