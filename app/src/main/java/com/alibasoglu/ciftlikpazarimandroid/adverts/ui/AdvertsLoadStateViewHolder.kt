package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.alibasoglu.ciftlikpazarimandroid.databinding.AdvertsLoadStateFooterViewItemBinding

class AdvertsLoadStateViewHolder(
    private val binding: AdvertsLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsgTextView.text = loadState.error.message
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsgTextView.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): AdvertsLoadStateViewHolder {
            return AdvertsLoadStateViewHolder(
                AdvertsLoadStateFooterViewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                retry
            )
        }
    }

}
