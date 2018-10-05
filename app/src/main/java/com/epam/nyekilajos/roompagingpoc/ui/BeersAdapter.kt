package com.epam.nyekilajos.roompagingpoc.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.epam.nyekilajos.roompagingpoc.databinding.BeerItemBinding
import com.epam.nyekilajos.roompagingpoc.model.database.Beer

class BeersAdapter : PagedListAdapter<Beer, BeerViewHolder>(BEERS_DIFF_CALLABCK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(BeerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.binding.beer = getItem(position)
    }
}

class BeerViewHolder(val binding: BeerItemBinding) : RecyclerView.ViewHolder(binding.root)

private val BEERS_DIFF_CALLABCK = object : DiffUtil.ItemCallback<Beer>() {

    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem == newItem
}
