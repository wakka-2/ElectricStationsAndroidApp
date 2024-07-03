package com.app.electricstations.util.network

interface INetworkDataListener {
    fun showLoadingProgress(requestID: Int)

    fun loadingFailure(throwable: Throwable=Throwable(), requestID: Int, errorId: Int,message:String="")

    fun responseDone(response: Any, requestID: Int)
    fun stopLoading()

}