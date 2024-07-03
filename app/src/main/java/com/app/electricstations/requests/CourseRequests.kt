package com.app.electricstations.requests

import android.content.Context
import com.app.electricstations.API.CourseService
import com.app.electricstations.model.RouteRequest
import com.app.electricstations.util.Constants
import com.app.electricstations.util.network.MainRequest
import com.khayat.app.util.network.ServiceGenerator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CourseRequests(context: Context, reqID: Int) :
    MainRequest(context = context, reqID = reqID) {
    fun makeRequest(
        routeRequest: RouteRequest?= null,
        code: String = "",
        firebaseToken: String = ""
    ) {
        iNetworkDataListener!!.showLoadingProgress(reqID)
        when (reqID) {
            Constants.GET_COURSES_REQUEST -> getCourses(routeRequest!!)


        }
    }
    private fun getCourses(routeRequest: RouteRequest?) {
        disposables += ServiceGenerator().getRetrofit(context).create(CourseService::class.java)
            .getCourses(routeRequest = routeRequest!!)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())
    }
}