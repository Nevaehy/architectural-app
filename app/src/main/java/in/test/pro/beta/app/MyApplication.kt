package `in`.test.pro.beta.app

import `in`.test.pro.beta.app.di.component.AppComponent
import `in`.test.pro.beta.app.di.component.DaggerAppComponent
import `in`.test.pro.beta.app.di.component.DataComponent
import android.app.Application
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication @Inject constructor() : Application() {

    private lateinit var appComponent: AppComponent
    var dataComponent: DataComponent? = null
        get() {
            if (field == null) {
                dataComponent = appComponent.getDataComponentBuilder().build()
            }
            return field
        }

    private val appScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        dataComponent = appComponent.getDataComponentBuilder().build()
    }
}