package com.epam.nyekilajos.roompagingpoc.ui

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.epam.nyekilajos.roompagingpoc.R
import com.epam.nyekilajos.roompagingpoc.databinding.ActivityMainBinding
import com.epam.nyekilajos.roompagingpoc.inject.DaggerActivityWithViewModel
import com.epam.nyekilajos.roompagingpoc.viewmodel.BeerListViewModel

class MainActivity : DaggerActivityWithViewModel() {

    private val beerListViewModel: BeerListViewModel by daggerViewModel()

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

        beerListViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
