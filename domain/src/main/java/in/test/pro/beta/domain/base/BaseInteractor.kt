package `in`.test.pro.beta.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseInteractor {
    protected val ioDispatcher = Dispatchers.IO
    protected val computationsDispatcher = Dispatchers.Default

    protected suspend fun <T> withIOContext(block: (suspend CoroutineScope.() -> T)): T {
        return withContext(ioDispatcher) {
            block()
        }
    }

    protected suspend fun <T> withComputationsContext(block: (suspend CoroutineScope.() -> T)): T {
        return withContext(computationsDispatcher) {
            block()
        }
    }

    /*
        This will work as fire and forget function as we are not waiting for it to complete or its response
     */
    protected suspend fun <T> withGlobalScope(block: suspend CoroutineScope.() -> T) {
        GlobalScope.launch(ioDispatcher) {
            block()
        }
    }

    protected inline fun <T : Any, S : Any> tryOutcome(
        tryAction: () -> T,
        onApiError: (RestApiException) -> S,
        onNetworkError: (NetworkErrorException) -> NetworkErrorException = { it }
    ): Outcome<T, S> =
        try {
            Outcome.Success(tryAction())
        } catch (e: RestApiException) {
            Outcome.Error(onApiError(e))
        } catch (exception: NetworkErrorException) {
            Outcome.NetworkConnection(onNetworkError(exception))
        }
}