package com.jeleniep.publicationManager.model.publications

import PublicationResponse
import retrofit2.Call
import retrofit2.http.GET


interface PublicationService {
    @GET("publications?")
    fun getCurrentWeatherData(): Call<List<PublicationResponse>>
//    fun getCurrentWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("APPID") app_id: String): Call<WeatherResponse>
}