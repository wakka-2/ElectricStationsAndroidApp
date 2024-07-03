package com.khayat.app.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.coroutines.CoroutineContext


/*
Created by Aiman Qaid on 21,نوفمبر,2020
Contact me at wakka-2@hotmail.com
*/
object Network {

    private var mMutableData : MutableLiveData<Boolean>? =null
    fun isNetworkAvailableWithInternetAccess(mContext: Context): LiveData<Boolean> {
        mMutableData = MutableLiveData()
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo?
        networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) isInternetAvailable()
        return mMutableData!!
    }

    private var parentJob = Job()
    private val coRoutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private fun isInternetAvailable() {
        val scope = CoroutineScope(coRoutineContext)
        scope.launch(Dispatchers.IO) {
            try {
                val sock = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)

                sock.connect(socketAddress, 2000) // This will block no more than timeoutMs
                sock.close()

                mMutableData!!.postValue(true)

            } catch (e: IOException) {
                mMutableData!!.postValue(false)
            }
        }
    }
}