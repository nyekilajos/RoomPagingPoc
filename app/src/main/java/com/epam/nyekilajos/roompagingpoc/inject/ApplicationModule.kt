package com.epam.nyekilajos.roompagingpoc.inject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.epam.nyekilajos.roompagingpoc.MainActivity
import com.epam.nyekilajos.roompagingpoc.RoomPagingApplication
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ApplicationModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun providesContext(application: RoomPagingApplication): Context = application.applicationContext

//        @Singleton
//        @JvmStatic
//        @Provides
//        fun providesAddressDataBase(context: Context): AddressDataBase {
//            return Room.databaseBuilder(context, AddressDataBase::class.java, ADDRESS_ITEM_DATABASE_NAME)
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
//                    .build()
//        }
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

//    @Binds
//    @IntoMap
//    @ViewModelKey(AddressListViewModel::class)
//    abstract fun bindAddressListViewModel(viewModel: AddressListViewModel): ViewModel

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

//private const val ADDRESS_ITEM_DATABASE_NAME = "addressItemDb"
