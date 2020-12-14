package com.jeleniep.publicationManager.model.publications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeleniep.PublicationManagerApplication
import com.jeleniep.publicationManager.interfaces.RequestObserver
import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.ui.publicationsList.PublicationListItem
import com.jeleniep.publicationManager.utils.SharedPreferencesHelper
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream

object PublicationRepository {
    private val publicationService: PublicationService = PublicationService.create()

    private val sharedPreferencesHelper =
        SharedPreferencesHelper(PublicationManagerApplication.appContext!!)


    fun getPublication(id: String): MutableLiveData<PublicationDTO> {
        val publicationData: MutableLiveData<PublicationDTO> = MutableLiveData()
        val authToken = sharedPreferencesHelper.getAuthToken()

        publicationService.getPublicationDetails(authToken, id)
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

    fun InputStream.toFile(path: String) {
        File(path).outputStream().use { this.copyTo(it) }
    }

    fun getPublicationPdf(id: String) {
        val authToken = sharedPreferencesHelper.getAuthToken()

        publicationService.getPublicationPdf(authToken, id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val inputStream: InputStream = response.body()!!.byteStream()
                        inputStream.toFile(PublicationManagerApplication.appContext!!.filesDir.absolutePath + "/" + id + ".pdf")
                        Log.d(
                            "debug",
                            PublicationManagerApplication.appContext!!.filesDir.absolutePath
                        );
                        File(PublicationManagerApplication.appContext!!.filesDir.absolutePath).listFiles().forEach { println(it) }


                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("debug", t.message);
                }

            })
    }

    fun deletePublication(
        id: String, observer: RequestObserver<PublicationDTO>
    ) {
        val authToken = sharedPreferencesHelper.getAuthToken()

        val publicationData: MutableLiveData<PublicationDTO> = MutableLiveData()
        publicationService.deletePublication(authToken, id)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.code() == 200) {
                        val publicationResponse = response.body()!!
                        observer.onSuccess(publicationResponse, "delete")
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    Log.d("debug", t.message);
                    publicationData.value = null
                    observer.onFail(ErrorResponse(), "delete")

                }

            })
    }

    fun getPublications(
        observer: RequestObserver<ArrayList<PublicationListItem>>? = null
    ): MutableLiveData<List<PublicationListItem>> {
        val listMutable: MutableLiveData<List<PublicationListItem>> = MutableLiveData()
        val list: ArrayList<PublicationListItem> = ArrayList()
        val authToken = sharedPreferencesHelper.getAuthToken()

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
                        observer?.onSuccess(list, "publicationList")

                    }
                }

                override fun onFailure(call: Call<List<PublicationDTO>>, t: Throwable) {
                    Log.d("debug", t.message);
                }

            })

        return listMutable
    }

    fun addPublication(
        publication: PublicationDTO,
        observer: RequestObserver<PublicationDTO>
    ) {
        val authToken = sharedPreferencesHelper.getAuthToken()

        publicationService.addPublication("Bearer $authToken", publication)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.isSuccessful) {
                        val publicationResponse = response.body()!!
                        observer.onSuccess(publicationResponse, "create")

                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        var errorResponse: ErrorResponse? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        observer.onFail(errorResponse, "create")
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    observer.onFail(ErrorResponse(), "create")
                }

            })
    }

    fun addPublicationFromPdf(
        file: MultipartBody.Part,
        observer: RequestObserver<PublicationDTO>
    ) {
        val authToken = sharedPreferencesHelper.getAuthToken()

        publicationService.addPublicationFromPdf("Bearer $authToken", file)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.isSuccessful) {
                        val publicationResponse = response.body()!!
                        observer.onSuccess(publicationResponse, "createFromPdf")

                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        var errorResponse: ErrorResponse? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        observer.onFail(errorResponse, "createFromPdf")
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    observer.onFail(ErrorResponse(), "createFromPdf")
                }

            })
    }

    fun editPublication(
        _id: String,
        publication: PublicationDTO,
        observer: RequestObserver<PublicationDTO>
    ) {
        val authToken = sharedPreferencesHelper.getAuthToken()

        publicationService.editPublication("Bearer $authToken", _id, publication)
            .enqueue(object : Callback<PublicationDTO> {
                override fun onResponse(
                    call: Call<PublicationDTO>,
                    response: Response<PublicationDTO>
                ) {
                    if (response.isSuccessful) {
                        val publicationResponse = response.body()!!
                        observer.onSuccess(publicationResponse, "update")

                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        var errorResponse: ErrorResponse? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        observer.onFail(errorResponse, "update")
                    }
                }

                override fun onFailure(call: Call<PublicationDTO>, t: Throwable) {
                    observer.onFail(ErrorResponse(), "update")
                }

            })
    }

}