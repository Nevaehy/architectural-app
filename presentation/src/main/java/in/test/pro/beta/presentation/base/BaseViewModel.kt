package `in`.test.pro.beta.presentation.base

import `in`.test.pro.beta.presentation.R
import `in`.test.pro.beta.presentation.utils.resourcemanager.ResourceManager
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    @Inject
    internal lateinit var resourceManager: ResourceManager

    val stateLiveData = MutableLiveData<State>()

    protected val appScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /**
     * Strictly use with viewModel.start method, since we don't want block of code executed again when fragment's view
     * is re-created (during navigation between fragments).
     *
     * Don't confuse [startOnce] as generic lambda function with any other methods
     */
    private var isStartMethodExecuted: Boolean = false
    protected fun startOnce(block: () -> Unit) {
        if (isStartMethodExecuted.not()) {
            isStartMethodExecuted = true
            block()
        }
    }

    protected inline fun launch(
        scope: CoroutineScope,
        crossinline block: suspend CoroutineScope.() -> Unit
    ): Job {
        return (scope + CoroutineExceptionHandler { _, _ -> }).launch(context = Dispatchers.Main) {
            runCatching { block() }
                .exceptionOrNull()
                ?.let { handleException(it) }
        }
    }

    @PublishedApi
    internal open suspend fun handleException(exception: Throwable) {
        exception.printStackTrace()
        val message = exception.message
        if (message.isNullOrEmpty().not()) {
            stateLiveData.postValue(State.Error(message!!))
        } else {
            stateLiveData.postValue(State.Error(resourceManager.getString(R.string.common_error_message)))
        }
        // log analytics exception here
    }
}

sealed class State {
    open class Error(open val message: String? = null) : State()
    open class Loading(open val message: String = "") : State()
    object Content : State()
}