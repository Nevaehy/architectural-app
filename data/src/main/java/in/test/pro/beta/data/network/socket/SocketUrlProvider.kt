package `in`.test.pro.beta.data.network.socket

interface SocketUrlProvider {

    fun getLiveFeedSocketUrl(): String

    fun getPortfolioStreamerSocketUrlV2(): String
}