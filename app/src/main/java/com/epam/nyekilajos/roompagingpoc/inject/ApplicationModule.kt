package com.epam.nyekilajos.roompagingpoc.inject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epam.nyekilajos.roompagingpoc.ui.MainActivity
import com.epam.nyekilajos.roompagingpoc.RoomPagingApplication
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.epam.nyekilajos.roompagingpoc.viewmodel.BeerListViewModel
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ApplicationModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun providesContext(application: RoomPagingApplication): Context = application.applicationContext

        @JvmStatic
        @Provides
        fun providesBeerService(): BeerService {
            return Retrofit.Builder()
                    .baseUrl(BEERS_SERVICE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BeerService::class.java)
        }

    }
}

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}

@Module
abstract class ViewModelBuilder {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BeerListViewModel::class)
    abstract fun bindAddressListViewModel(viewModel: BeerListViewModel): ViewModel

}

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class,
    ViewModelBuilder::class
])
interface AppComponent : AndroidInjector<RoomPagingApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RoomPagingApplication>()
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

private const val BEERS_SERVICE_BASE_URL = "https://api.punkapi.com/v2/"
