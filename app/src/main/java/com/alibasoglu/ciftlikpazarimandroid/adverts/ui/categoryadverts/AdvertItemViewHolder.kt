package com.alibasoglu.ciftlikpazarimandroid.adverts.ui.categoryadverts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alibasoglu.ciftlikpazarimandroid.adverts.domain.Advert
import com.alibasoglu.ciftlikpazarimandroid.databinding.ItemAdvertBinding
import com.alibasoglu.ciftlikpazarimandroid.utils.decodeBase64Image

class AdvertItemViewHolder(
    private val binding: ItemAdvertBinding,
    private val listener: AdvertClickListener
) : ViewHolder(binding.root) {

    fun bind(advert: Advert) {
        val name = advert.name
        val price = "${advert.price} TL"
        val image = decodeBase64Image(advert.images[0])

        with(binding) {
            root.setOnClickListener {
                listener.onClick(advert)
            }
            advertNameTextView.text = name
            cityTextView.text = advert.city
            priceTextView.text = price
            advertImageView.setImageBitmap(image)
        }
    }

    fun interface AdvertClickListener {
        fun onClick(advert: Advert)
    }

    companion object {
        fun create(parent: ViewGroup, listener: AdvertClickListener): AdvertItemViewHolder {
            return AdvertItemViewHolder(
                ItemAdvertBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                listener
            )
        }
    }

}
