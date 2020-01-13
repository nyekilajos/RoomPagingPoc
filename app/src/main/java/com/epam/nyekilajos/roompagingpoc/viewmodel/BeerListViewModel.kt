package com.epam.nyekilajos.roompagingpoc.viewmodel

import androidx.lifecycle.*
import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.network.BeerServiceException
import com.epam.nyekilajos.roompagingpoc.repository.BeerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class BeerListViewModel @Inject constructor(private val beerRepository: BeerRepository) : ViewModel() {

    val beers: LiveData<List<Beer>> = liveData(Dispatchers.IO) {
        beerRepository.getBeers()
                .handleErrors(emptyList())
                .collect {
                    emit(it)
                    loading.postValue(false)
                }
    }

    val loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }

    val error: MutableLiveData<String> = MutableLiveData()

    fun refreshBeers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                beerRepository.refreshBeers()
            } catch (e: IOException) {
                error.postValue(e.localizedMessage)
                loading.postValue(false)
            } catch (e: BeerServiceException) {
                error.postValue(e.localizedMessage)
                loading.postValue(false)
            }
        }
    }

    private fun <T> Flow<T>.handleErrors(defaultValue: T): Flow<T> = flow {
        try {
            collect { emit(it) }
        } catch (e: IOException) {
            error.postValue(e.localizedMessage)
            loading.postValue(false)
            emit(defaultValue)
        } catch (e: BeerServiceException) {
            error.postValue(e.localizedMessage)
            loading.postValue(false)
            emit(defaultValue)
        }
    }
}
