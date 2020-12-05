package com.jeleniep.publicationManager.model.publications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeleniep.publicationManager.interfaces.PublicationListObserver
import com.jeleniep.publicationManager.interfaces.RequestObserver
import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.ui.publicationsList.PublicationListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PublicationRepository {
    private val publicationService: PublicationService = PublicationService.create()

    fun getPublication(id: String): MutableLiveData<PublicationDTO> {
        val publicationData: MutableLiveData<PublicationDTO> = MutableLiveData()
        publicationService.getPublicationDetails(id)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.code() == 200) {
                        val publicationResponse = response.body()!!
                        Log.d("debug123a", publicationResponse!!.name)
                        publicationData.value = publicationResponse!!
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    Log.d("debug", t.message);
                    publicationData.value = null
                }

            })
        return publicationData
    }

    fun getPublications(
        authToken: String,
        observer: RequestObserver<ArrayList<PublicationListItem>>? = null
    ): MutableLiveData<List<PublicationListItem>> {
        val listMutable: MutableLiveData<List<PublicationListItem>> = MutableLiveData()
        val list: ArrayList<PublicationListItem> = ArrayList()
        publicationService.getPublications("Bearer $authToken")
            .enqueue(object : Callback<List<PublicationDTO>> {
                override fun onResponse(
                    call: Call<List<PublicationDTO>>,
                    response: Response<List<PublicationDTO>>
                ) {
                    if (response.code() == 200) {
                        val publicationResponse = response.body()!!
                        for (publication in publicationResponse) {
                            val authors = publication.authors!!.joinToString(separator = ", ")
                            val item: PublicationListItem = PublicationListItem(
                                publication._id!!,
                                publication.name!!,
                                authors
                            )
                            list.add(item);
                        }
                        listMutable.value = list
                        observer?.onSuccess(list)

                    }
                }

                override fun onFailure(call: Call<List<PublicationDTO>>, t: Throwable) {
                    Log.d("debug", t.message);
                }

            })

        return listMutable
    }

    fun addPublication(
        authToken: String,
        publication: PublicationDTO,
        observer: PublicationListObserver
    ) {
        publicationService.addPublication("Bearer $authToken", publication)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.isSuccessful) {
                        val publicationResponse = response.body()!!
                        observer.onPublicationUpdateSuccess(publicationResponse,"create")

                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        var errorResponse: ErrorResponse? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        observer.onPublicationUpdateFail(errorResponse,"create")
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    observer.onPublicationUpdateFail(ErrorResponse(),"create")
                }

            })
    }

    fun editPublication(
        authToken: String,
        _id: String,
        publication: PublicationDTO,
        observer: PublicationListObserver
    ) {
        publicationService.editPublication("Bearer $authToken", _id, publication)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.isSuccessful) {
                        val publicationResponse = response.body()!!
                        observer.onPublicationUpdateSuccess(publicationResponse, "update")

                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        var errorResponse: ErrorResponse? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        observer.onPublicationUpdateFail(errorResponse, "update")
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    observer.onPublicationUpdateFail(ErrorResponse(), "update")
                }

            })
    }

}