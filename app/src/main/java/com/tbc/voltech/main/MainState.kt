package com.tbc.voltech.main

data class MainActivityState(
    val isAuthorized: Boolean? = null,
){
    val isLoading:Boolean
        get() = isAuthorized == null
}
