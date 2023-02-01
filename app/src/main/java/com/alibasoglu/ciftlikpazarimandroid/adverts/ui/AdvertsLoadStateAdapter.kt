package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class AdvertsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<AdvertsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: AdvertsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): AdvertsLoadStateViewHolder {
        return AdvertsLoadStateViewHolder.create(parent, retry)
    }
}
