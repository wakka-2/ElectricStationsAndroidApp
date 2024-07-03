package com.app.electricstations.API

import com.app.electricstations.model.RouteRequest
import com.app.electricstations.model.RouteResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/*
Created by Aiman Qaid on 17,يوليو,2020
Contact me at wakka-2@hotmail.com
*/
interface CourseService {

    @POST("calculate_route")
    fun getCourses(@Body routeRequest: RouteRequest): Observable<RouteResponse>
//    @POST("api/v1/customer/check-token-customer/")
//    fun checkToken(): Observable<LoginResponse>
//
//    @POST("api/v1/customer/profile/")
//    fun profile(): Observable<ProfileResponse>
//
//    @FormUrlEncoded
//    @POST("api/v1/customer/profile/edit/")
//    fun editProfile(
//        @Field("first_name") name:String,
//        @Field("last_name") lastName:String,
//        @Field("email") email:String,
//        @Field("gender") gender:Int
//    ): Observable<LoginResponse>
//
//
//
//    @GET("api/v1/customer/terms/")
//    fun terms(): Observable<TermsResponse>
//
//    @POST("api/v1/customer/logout/")
//    fun logout(): Observable<LoginResponse>
}
