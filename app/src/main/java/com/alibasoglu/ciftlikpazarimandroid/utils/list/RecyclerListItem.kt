package com.alibasoglu.ciftlikpazarimandroid.utils.list

interface RecyclerListItem {
    infix fun areItemsTheSame(other: RecyclerListItem): Boolean
    infix fun areContentsTheSame(other: RecyclerListItem): Boolean
}
