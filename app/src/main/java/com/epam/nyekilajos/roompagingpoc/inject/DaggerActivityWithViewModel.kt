package com.epam.nyekilajos.roompagingpoc.inject

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class DaggerActivityWithViewModel : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    inline fun <reified T : ViewModel> daggerViewModel() = DaggerViewModelActivityDelegate(this, T::class)

}

class DaggerViewModelActivityDelegate<T : ViewModel>(
        private val activity: DaggerActivityWithViewModel,
        private val kClass: KClass<T>
) : DefaultLifecycleObserver {

    private var viewModel: T? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        viewModel = ViewModelProvider(activity, activity.viewModelFactory).get(kClass.java)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return viewModel ?: throw IllegalStateException("Activity is not started yet.")
    }
}
