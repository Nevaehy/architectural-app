package `in`.test.pro.beta.data.base

class NetworkErrorException : Exception()
class RestApiException(override val message: String, val errorCode: Int) : Exception()
class SocketApiException(override val message: String, val errorCode: Int) : Exception()