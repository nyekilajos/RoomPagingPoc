package com.epam.nyekilajos.roompagingpoc

import android.os.Bundle
import android.util.Log
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var beerService: BeerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Single
                .fromCallable { beerService.getBeers().execute() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.e(MainActivity::class.java.simpleName, it.body().toString())
                        },
                        onError = {
                            Log.e(MainActivity::class.java.simpleName, it.toString())
                        }
                )
    }
}
