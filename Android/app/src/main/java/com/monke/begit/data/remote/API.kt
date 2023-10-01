package com.monke.begit.data.remote


import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date

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
    suspend fun getFundById(@Query("fundId") findId: Int): Response<Any?>

    @GET("/get_subdivision_by_id")
    @Headers("Content-Type: application/json")
    suspend fun getSubdivisionById(@Query("subdivisionId") subdivisionId: Int): Response<Any?>

    data class RequestBodyRegister(var username:String, var password: String,
                                   var email: String, var name: String)
    @POST("/register_user")
    @Headers("Content-Type: application/json")
    suspend fun registerUser(@Body body: PostUserRemote): Response<Unit>

    @POST("/register_supervisor")
    @Headers("Content-Type: application/json")
    suspend fun registerSupervisor(@Body body: PostUserRemote): Response<Unit>

    @POST("/login")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(@Body body: LoginUserRemote): Response<GetUserRemote>

    @POST("/login_supervisor")
    @Headers("Content-Type: application/json")
    suspend fun loginSupervisor(@Body body: LoginUserRemote): Response<GetUserRemote>

    data class RequestBodySelectFund(var fundId: Int)
    @POST("/select_fund")
    @Headers("Content-Type: application/json")
    suspend fun selectFund(@Body body: RequestBodySelectFund): Single<Any?>

    data class RequestBodySelectSubdivision(var subdivisionId:Int)
    @POST("/select_subdivision")
    @Headers("Content-Type: application/json")
    suspend fun selectSubdivision(@Body body: RequestBodySelectSubdivision): Single<Any?>


    data class RequestBodyAddActivity(var typeId: Int, val value: Int, var date: String)
    @POST("/add_activity")
    @Headers("Content-Type: application/json")
    suspend fun addActivity(@Body body: RequestBodyAddActivity): Response<Unit>
}