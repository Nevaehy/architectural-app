package `in`.test.pro.beta.data.network.socket.client

class SocketResponse<T>(
    val data: T? = null,
    val error: SocketError? = null
) {

    data class SocketError(
        val code: Int? = 0,
        val message: String? = null
    )
}

