package com.example.countrylist.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


interface ConnectivityInterceptor{
    fun isOnline():Boolean
}
class ConnectivityInterceptorImpl (
    private var application: Context
) : ConnectivityInterceptor {

    override fun isOnline(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        var res = false
        connectivityManager.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                res = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return res
    }
}