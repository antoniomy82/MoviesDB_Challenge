package com.antoniomy82.moviesdb_challenge.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.antoniomy82.moviesdb_challenge.R
import java.util.*

class CommonUtil {
    companion object {

        var actualPage=1

        fun replaceFragment(fragment: Fragment?, fragmentManager: FragmentManager) {
            try {
                val transaction = fragmentManager.beginTransaction()
                fragment?.let { transaction.replace(R.id.frame_container, it) }
                transaction.commit()
            } catch (e: Exception) {
                Log.e("__replaceFragment", e.toString())
            }
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {

                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true

                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }

            return false
        }

        fun getLanguage():String{
            return Locale.getDefault().toLanguageTag()
        }

    }
}