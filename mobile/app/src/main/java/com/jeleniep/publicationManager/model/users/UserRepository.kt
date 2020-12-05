package com.jeleniep.publicationManager.model.publications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PublicationRepository {
    private val publicationService: PublicationService = PublicationService.create()

    fun getPublication(id: String): MutableLiveData<PublicationResponse> {
        val publicationData: MutableLiveData<PublicationResponse> = MutableLiveData()
        publicationService.getPublicationDetails(id).enqueue(object : Callback<PublicationResponse> {
            override fun onResponse(
                call: Call<PublicationResponse>,
                response: Response<PublicationResponse>
            ) {
                if (response.code() == 200) {
                    val publicationResponse = response.body()!!
                    Log.d("debug123a", publicationResponse!!.name)
                    publicationData.value = publicationResponse!!
                }
            }

            override fun onFailure(call: Call<PublicationResponse>, t: Throwable) {
                Log.d("debug", t.message);
                publicationData.value = null
            }

        })

        return publicationData

//        .enqueue(object : Callback<PublicationResponse>() {
//            override fun onResponse(
//                call: Call<NewsResponse?>?,
//                response: Response<NewsResponse?>
//            ) {
//                if (response.isSuccessful()) {
//                    newsData.setValue(response.body())
//                }
//            }
//
//            override fun onFailure(call: Call<NewsResponse?>?, t: Throwable?) {
//                newsData.setValue(null)
//            }
//        })
    }

}