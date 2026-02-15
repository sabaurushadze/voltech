package com.tbc.core.data.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.tbc.core.domain.network.ConnectivityObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectivityObserverImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : ConnectivityObserver {
    private val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

    override fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        ) == true
    }
}