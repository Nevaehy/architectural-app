package `in`.test.pro.beta.domain.base

sealed class Outcome<out T : Any, out S : Any> {
    data class Success<out T : Any>(val data: T) : Outcome<T, Nothing>()
    data class Error<out S : Any>(val reason: S) : Outcome<Nothing, S>()
    class NetworkConnection(val cause: NetworkErrorException? = null) : Outcome<Nothing, Nothing>()
}