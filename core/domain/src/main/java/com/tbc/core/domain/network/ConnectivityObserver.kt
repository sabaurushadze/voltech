package com.tbc.core.domain.network

interface ConnectivityObserver {
    fun isConnected(): Boolean
}