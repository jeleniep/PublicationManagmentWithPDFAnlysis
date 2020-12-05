package com.jeleniep.publicationManager.ui.publicationsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jeleniep.PublicationManagerApplication
import com.jeleniep.publicationManager.interfaces.RequestObserver
import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.model.publications.PublicationRepository
import com.jeleniep.publicationManager.utils.SharedPreferencesHelper

class PublicationsListModel : ViewModel(), RequestObserver<ArrayList<PublicationListItem>> {

    private val _publications: MutableLiveData<List<PublicationListItem>> by lazy {
        val sharedPreferencesHelper =
            SharedPreferencesHelper(PublicationManagerApplication.appContext!!)
        val list = PublicationRepository.getPublications(sharedPreferencesHelper.getAuthToken())
        list
    }
    var publications: LiveData<List<PublicationListItem>> = _publications

    fun refreshList() {
        val sharedPreferencesHelper =
            SharedPreferencesHelper(PublicationManagerApplication.appContext!!)
        PublicationRepository.getPublications(sharedPreferencesHelper.getAuthToken(), this)
    }

    override fun onSuccess(response: ArrayList<PublicationListItem>) {
        _publications.postValue(response)
    }

    override fun onFail(errorResponse: ErrorResponse?, type: String) {
        TODO("Not yet implemented")
    }

}