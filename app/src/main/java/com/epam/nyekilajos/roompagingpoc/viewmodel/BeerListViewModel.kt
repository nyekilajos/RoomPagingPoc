package com.epam.nyekilajos.roompagingpoc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.repository.BeerRepository
import javax.inject.Inject

class BeerListViewModel @Inject constructor(beerRepository: BeerRepository) : ViewModel() {

    val beers: LiveData<List<Beer>> = beerRepository.getBeers().toLiveData()

}