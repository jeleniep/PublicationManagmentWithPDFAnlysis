package com.jeleniep.publicationManager.network.users

import com.jeleniep.publicationManager.utils.BaseService
import retrofit2.Call
import retrofit2.http.*


interface UserService {

    @POST("users/auth")
    fun login(@Body loginBody: LoginBody): Call<UserDTO>

    @POST("users/logout")
    fun logout(@Header("Authorization") authToken: String): Call<UserDTO>

    @GET("users/me")
    fun checkUser(@Header("Authorization") authToken: String): Call<UserDTO>

    @GET("users/{id}")
    fun getUserDetails(@Header("Authorization") authToken: String, @Path("id") id: String): Call<UserDTO>

    companion object {
        fun create(): UserService {
            return BaseService.service.create(UserService::class.java)
        }
    }

}