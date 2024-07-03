package com.app.electricstations.util.network

import android.content.Context
import androidx.annotation.NonNull
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.app.electricstations.util.Constants
import com.khayat.app.util.network.ServiceGenerator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

open class MainRequest(val context: Context, var reqID: Int) {
    val disposables = CompositeDisposable()
    var iNetworkDataListener: INetworkDataListener? = null
    var serviceGenerator: ServiceGenerator? = null

    init {
        serviceGenerator = ServiceGenerator()
    }

    fun handleRequestFailure(throwable: Throwable) {
        throwable.printStackTrace()
        try {
            if (throwable is HttpException) {
                val code = throwable.response()!!.code()
                if (code == 503) {
                    if (iNetworkDataListener != null) {
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.SERVICE_NOT_AVAILABLE)
                    }
                } else if (code == 401) {
                    if (iNetworkDataListener != null) {
                        val message = throwable.response()!!.message().toString()
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == 500) {
                    if (iNetworkDataListener != null) {
                        val message = throwable.response()!!.message().toString()
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == 502) {
                    if (iNetworkDataListener != null) {
                        val message = throwable.response()!!.message().toString()
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == -100) {
                    if (iNetworkDataListener != null) {
                        val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == -101) {
                    if (iNetworkDataListener != null) {
                        val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == -102) {
                    if (iNetworkDataListener != null) {
                        val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == -20) {
                    if (iNetworkDataListener != null) {
                        val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == -2) {
                    if (iNetworkDataListener != null) {
                        val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )
                    }
                } else if (code == 400) {
                    val message = ""
                    if (iNetworkDataListener != null) {
                        iNetworkDataListener!!.stopLoading()
                        iNetworkDataListener!!.loadingFailure(
                            requestID = reqID,
                            errorId = Constants.ERROR_WITH_MESSAGE,
                            message = message
                        )

                    }
                } else {
                    val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                    try {
                        if (iNetworkDataListener != null) {
                            iNetworkDataListener!!.stopLoading()
                            iNetworkDataListener!!.loadingFailure(
                                requestID = reqID,
                                errorId = Constants.ERROR_WITH_MESSAGE,
                                message = message
                            )
                        }
                    } catch (e: Exception) {
                        try {
                            val message = JSONObject(throwable.response()!!.errorBody()!!.string()).get("message") as String
                            if (iNetworkDataListener != null) {
                                iNetworkDataListener!!.stopLoading()
                                iNetworkDataListener!!.loadingFailure(
                                    requestID = reqID,
                                    errorId = Constants.ERROR_WITH_MESSAGE,
                                    message =message
                                )

                            }
                        } catch (ex: Exception) {
                            if (iNetworkDataListener != null) {
                                iNetworkDataListener!!.stopLoading()
                                iNetworkDataListener!!.loadingFailure(
                                    requestID = reqID,
                                    errorId = Constants.UN_KNOWN_ERROR
                                )
                            }
                        }

                    }

                }

            } else if (throwable is SocketTimeoutException) {
                if (iNetworkDataListener != null) {
                    iNetworkDataListener!!.stopLoading()
                    iNetworkDataListener!!.loadingFailure(
                        requestID = reqID,
                        errorId = Constants.CONNECTION_TIME_OUT
                    )
                }
            } else if (throwable is IOException) {
                if (iNetworkDataListener != null) {
                    iNetworkDataListener!!.stopLoading()
                    iNetworkDataListener!!.loadingFailure(
                        requestID = reqID,
                        errorId = Constants.CONNECTION_ERROR
                    )
                }
            } else {
                if (iNetworkDataListener != null) {
                    iNetworkDataListener!!.stopLoading()
                    iNetworkDataListener!!.loadingFailure(
                        requestID = reqID,
                        errorId = Constants.UN_KNOWN_ERROR
                    )
                }
            }

        } catch (e: Exception) {
            iNetworkDataListener!!.stopLoading()
            e.printStackTrace()
        }

    }

    fun getObserver(): DisposableObserver<Any> {
        return object : DisposableObserver<Any>() {

            override fun onNext(@NonNull response: Any) {
                iNetworkDataListener!!.responseDone(response, reqID)
            }

            override fun onError(@NonNull e: Throwable) {
                handleRequestFailure(e)
            }

            override fun onComplete() {
                iNetworkDataListener!!.stopLoading()
            }
        }
    }

    fun stop() {
        disposables.clear()
    }
}