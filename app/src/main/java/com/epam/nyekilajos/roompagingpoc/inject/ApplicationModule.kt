package com.epam.nyekilajos.roompagingpoc.inject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.epam.nyekilajos.roompagingpoc.RoomPagingApplication
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.database.MIGRATION_1_2
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.epam.nyekilajos.roompagingpoc.ui.MainActivity
import com.epam.nyekilajos.roompagingpoc.viewmodel.BeerListViewModel
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
                    .client(OkHttpClient.Builder()
                            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BeerService::class.java)
        }

        @JvmStatic
        @Provides
        fun providesBeersDatabase(context: Context): BeersDatabase {
            return Room
                    .databaseBuilder(context, BeersDatabase::class.java, BEERS_DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
        }

    }
}

@FlowPreview
@ExperimentalCoroutinesApi
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}

@FlowPreview
@ExperimentalCoroutinesApi
@Module
abstract class ViewModelBuilder {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BeerListViewModel::class)
    abstract fun bindBeerListViewModel(viewModel: BeerListViewModel): ViewModel

}

@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class,
    ViewModelBuilder::class
])
interface AppComponent : AndroidInjector<RoomPagingApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<RoomPagingApplication>
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

private const val BEERS_SERVICE_BASE_URL = "https://api.punkapi.com/v2/"
private const val BEERS_DATABASE_NAME = "beersDb"
private const val TIMEOUT = 25000L
