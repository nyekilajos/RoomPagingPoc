package com.epam.nyekilajos.roompagingpoc.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.epam.nyekilajos.roompagingpoc.R
import com.epam.nyekilajos.roompagingpoc.databinding.ActivityMainBinding
import com.epam.nyekilajos.roompagingpoc.inject.DaggerActivityWithViewModel
import com.epam.nyekilajos.roompagingpoc.inject.daggerViewModel
import com.epam.nyekilajos.roompagingpoc.viewmodel.BeerListViewModel

class MainActivity : DaggerActivityWithViewModel() {

    private val beerListViewModel: BeerListViewModel by daggerViewModel(this)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding.setLifecycleOwner(this)
        binding.adapter = BeersAdapter()
        binding.vm = beerListViewModel
    }
}
