package dev.daneeskripter.cernyrobin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class InternetChecker {

    fun checkForInternet(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true

            else -> return false
        }
    }
}