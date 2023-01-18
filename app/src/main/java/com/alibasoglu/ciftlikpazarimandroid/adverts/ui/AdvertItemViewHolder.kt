package com.alibasoglu.ciftlikpazarimandroid.adverts.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.databinding.ItemAdvertBinding

class AdvertItemViewHolder(
    private val binding: ItemAdvertBinding
) : ViewHolder(binding.root) {

    fun bind(advert: Advert) {
        val name = advert.name
        val price = "${advert.price} TL"

        with(binding) {
            advertNameTextView.text = name
            cityTextView.text = advert.city
            priceTextView.text = price
        }
    }

    companion object {
        fun create(parent: ViewGroup): AdvertItemViewHolder {
            return AdvertItemViewHolder(
                ItemAdvertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

}
