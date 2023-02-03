package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.utils.list.BaseDiffUtil

class AdvertsAdapter(
    private val listener: CategoryAdvertsAdapterListener
) : PagingDataAdapter<Advert, AdvertItemViewHolder>(BaseDiffUtil()) {

    override fun onBindViewHolder(holder: AdvertItemViewHolder, position: Int) {
        getItem(position)?.let { advert ->
            holder.bind(advert)
        }
    }

    private val advertClickItem = AdvertItemViewHolder.AdvertClickListener { advertItem ->
        listener.onAdvertClick(advertItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertItemViewHolder {
        return AdvertItemViewHolder.create(parent, advertClickItem)
    }

    interface CategoryAdvertsAdapterListener {
        fun onAdvertClick(advert: Advert)
    }
}
