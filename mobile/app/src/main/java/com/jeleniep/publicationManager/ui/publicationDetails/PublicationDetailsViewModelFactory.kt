package com.jeleniep.publicationManager.ui.publicationDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PublicationDetailsViewModelFactory(private val _id: String?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PublicationDetailsViewModel::class.java)) {
            return PublicationDetailsViewModel(_id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}