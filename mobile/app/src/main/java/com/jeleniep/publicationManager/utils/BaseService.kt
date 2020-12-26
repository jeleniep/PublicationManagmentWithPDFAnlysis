package com.jeleniep.publicationManager.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseService {
    val service: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.16:3000/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}