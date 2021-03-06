package com.epam.nyekilajos.roompagingpoc.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("itemDecoration")
fun setItemDecoration(recyclerView: RecyclerView, itemDecoration: RecyclerView.ItemDecoration) {
    recyclerView.addItemDecoration(itemDecoration)
}

@BindingAdapter(value = ["adapter", "layoutManager", "data"], requireAll = false)
@Suppress("UNCHECKED_CAST")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, adapter: ListAdapter<T, *>?, layoutManager: RecyclerView.LayoutManager?, data: List<T>?) {
    if (adapter != null) {
        recyclerView.adapter = adapter
    }
    if (layoutManager != null) {
        recyclerView.layoutManager = layoutManager
        if (data != null) {
            (recyclerView.adapter as ListAdapter<T, *>?)?.submitList(data)
        }
    }
}

@BindingAdapter("onRefresh")
fun setOnRefreshListener(swipeRefreshLayout: SwipeRefreshLayout, onRefresh: Runnable) {
    swipeRefreshLayout.setOnRefreshListener {
        onRefresh.run()
    }
}

@BindingAdapter("refreshing")
fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = refreshing
}
