package com.monke.begit.data.remote


import com.monke.begit.data.remote.Fund
import com.monke.begit.data.remote.PhysicalActivity
import com.monke.begit.data.remote.User
import com.monke.begit.data.remote.UserTop
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

// API для сервера приложения

interface API {
    @GET("/get_activities")
    @Headers("Content-Type: application/json")
    suspend fun getActivities(): Response<ArrayList<PhysicalActivity>>

    @GET("/get_top")
    @Headers("Content-Type: application/json")
    suspend fun getTop(): Response<ArrayList<UserTop>>

    @GET("/get_activities_supervisor")
    @Headers("Content-Type: application/json")
    suspend fun getActivitiesSupervisor(): Response<ArrayList<PhysicalActivity>>

    @GET("/get_funds")
    @Headers("Content-Type: application/json")
    suspend fun getFunds(): Response<ArrayList<Fund>>

    @GET("/get_fund_by_id")
    @Headers("Content-Type: application/json")
    suspend fun getFundById(@Query("fundId") findId: Int): Response<String>

    @GET("/get_subdivision_by_id")
    @Headers("Content-Type: application/json")
    suspend fun getSubdivisionById(@Query("subdivisionId") subdivisionId: Int): Response<String>

    data class RequestBodyRegister(var username:String, var password: String,
                                   var email: String, var name: String)
    @POST("/register_user")
    @Headers("Content-Type: application/json")
    suspend fun registerUser(@Body body: RequestBodyRegister): Response<String>

    @POST("/register_supervisor")
    @Headers("Content-Type: application/json")
    suspend fun registerSupervisor(@Body body: RequestBodyRegister): Response<String>

    data class RequestBodyLogin(var username:String, var password: String)
    @POST("/login")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(@Body body: RequestBodyLogin): Response<User>

    @POST("/login_supervisor")
    @Headers("Content-Type: application/json")
    suspend fun loginSupervisor(@Body body: RequestBodyLogin): Response<User>

    data class RequestBodySelectFund(var fundId: Int)
    @POST("/select_fund")
    @Headers("Content-Type: application/json")
    suspend fun selectFund(@Body body: RequestBodySelectFund): Single<String>

    data class RequestBodySelectSubdivision(var subdivisionId:Int)
    @POST("/select_subdivision")
    @Headers("Content-Type: application/json")
    suspend fun selectSubdivision(@Body body: RequestBodySelectSubdivision): Single<String>


    data class RequestBodyAddActivity(var typeId: Int, val value: Double, var data: String)
    @POST("/add_activity")
    @Headers("Content-Type: application/json")
    suspend fun addActivity(@Body body: RequestBodyAddActivity): Single<String>
}