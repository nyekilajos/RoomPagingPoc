package com.epam.nyekilajos.roompagingpoc

import com.epam.nyekilajos.roompagingpoc.inject.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class RoomPagingApplication : DaggerApplication() {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.factory().create(this)

}
