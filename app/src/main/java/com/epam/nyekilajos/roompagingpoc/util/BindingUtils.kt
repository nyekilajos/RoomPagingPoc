package com.epam.nyekilajos.roompagingpoc.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("itemDecoration")
fun setItemDecoration(recyclerView: RecyclerView, itemDecoration: RecyclerView.ItemDecoration) {
    recyclerView.addItemDecoration(itemDecoration)
}

@BindingAdapter("itemAnimator")
fun setItemAnimator(recyclerView: RecyclerView, animator: RecyclerView.ItemAnimator) {
    recyclerView.itemAnimator = animator
}
