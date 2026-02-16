package com.tbc.core.data.remote.network

import com.tbc.core.domain.network.ConnectivityObserver
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityObserver.isConnected()) {
            throw IOException("No network connection")
        }
        return chain.proceed(chain.request())
    }
}