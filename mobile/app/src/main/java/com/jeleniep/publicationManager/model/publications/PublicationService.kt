package com.jeleniep.publicationManager.model.publications

import com.jeleniep.publicationManager.utils.BaseService
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface PublicationService {
    @GET("publications")
    fun getPublications(@Header("Authorization") authToken: String): Call<List<PublicationDTO>>

    @POST("publications")
    fun addPublication(@Header("Authorization") authToken: String, @Body publication: PublicationDTO): Call<PublicationDTO>

    @Multipart
    @POST("publications/fromPdf")
    fun addPublicationFromPdf(
        @Header("Authorization") authToken: String,
        @Part file: MultipartBody.Part
    ): Call<PublicationDTO>

    @GET("publications/{id}")
    fun getPublicationDetails(@Header("Authorization") authToken: String, @Path("id") id: String): Call<PublicationDTO>

    @GET("publications/{id}/pdf")
    fun getPublicationPdf(@Header("Authorization") authToken: String, @Path("id") id: String): Call<ResponseBody>

    @DELETE("publications/{id}")
    fun deletePublication(@Header("Authorization") authToken: String, @Path("id") id: String): Call<PublicationDTO>

    @PUT("publications/{id}")
    fun editPublication(@Header("Authorization") authToken: String, @Path("id") id: String, @Body publication: PublicationDTO): Call<PublicationDTO>

    companion object {
        fun create(): PublicationService {
            return BaseService.service.create(PublicationService::class.java)
        }

    }

}