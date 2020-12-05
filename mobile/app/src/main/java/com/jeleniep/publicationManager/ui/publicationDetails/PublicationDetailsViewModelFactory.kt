package com.jeleniep.publicationManager.ui.publicationDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PublicationDetailsViewModelFactory : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is details ment"
    }
    val text: LiveData<String> = _text
}