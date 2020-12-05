package com.jeleniep.publicationManager.model.publications

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PublicationService {
    @GET("publications")
    fun getPublications(): Call<List<PublicationResponse>>

    @GET("publications/{id}")
    fun getPublicationDetails(@Path("id") id: String): Call<PublicationResponse>

    companion object {
        fun create(): PublicationService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.21:3000/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(PublicationService::class.java)
        }

    }

}