package com.epam.nyekilajos.roompagingpoc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.repository.BeerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BeerListViewModel @Inject constructor(private val beerRepository: BeerRepository) : ViewModel() {

    val beers: LiveData<List<Beer>> = beerRepository
            .getBeers()
            .doOnNext { loading.value = false }
            .toLiveData()

    val loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }

    val error: MutableLiveData<String> = MutableLiveData()

    private val disposables = CompositeDisposable()

    fun refreshBeers() {
        disposables += beerRepository
                .refreshBeers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {
                            error.value = it.localizedMessage
                            loading.value = false
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
