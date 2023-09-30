package com.monke.begit.data.remote


import com.monke.begit.data.remote.Fund
import com.monke.begit.data.remote.PhysicalActivity
import com.monke.begit.data.remote.User
import com.monke.begit.data.remote.UserTop
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

// API для сервера приложения
interface API {

    @GET("/LoginUser")
    @Headers("Content-Type: application/json")
    fun loginUser(@Query("username") username: String,
                  @Query("password") password: String): Single<User>

    @GET("/LoginSupervisor")
    @Headers("Content-Type: application/json")
    fun loginSupervisor(@Query("username") username: String,
                        @Query("password") password: String): Single<User>

    @GET("/GetActivities")
    @Headers("Content-Type: application/json")
    fun getActivities(@Query("userId") userId: Int): Single<ArrayList<PhysicalActivity>>

    @GET("/GetTop")
    @Headers("Content-Type: application/json")
    fun getTop(): Single<ArrayList<UserTop>>

    @GET("/GetActivitiesSupervisor")
    @Headers("Content-Type: application/json")
    fun getTop(@Query("id") idSubdivision: Int): Single<ArrayList<PhysicalActivity>>

    @GET("/GetFunds")
    @Headers("Content-Type: application/json")
    fun getFunds(): Single<ArrayList<Fund>>

    @POST("/RegisterUser")
    @Headers("Content-Type: application/json")
    fun registerUser(@Query("username") username: String,
                     @Query("password") password: String,
                     @Query("email") email: String,
                     @Query("name") name:String): Single<Boolean>

    @POST("/RegisterSupervisor")
    @Headers("Content-Type: application/json")
    fun registerSupervisor(@Query("username") username: String,
                           @Query("password") password: String,
                           @Query("email") email: String,
                           @Query("name") name:String): Single<Boolean>

    @POST("/SelectFund")
    @Headers("Content-Type: application/json")
    fun selectFund(@Query("username") username:String,
                   @Query("id") funId: Int): Single<Boolean>

    @POST("/SelectSubdivision")
    @Headers("Content-Type: application/json")
    fun selectSubdivision(@Query("username") username:String,
                   @Query("id") funId: Int): Single<Boolean>

    @POST("/AddActivity")
    @Headers("Content-Type: application/json")
    fun addActivity(@Query("userId") userId: Int,
                    @Query("typeId") typeId: Int,
                    @Query("value") value: Double,
                    @Query("date") date: String): Single<Boolean>
}