package com.jeleniep.publicationManager.ui.publicationNew

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PublicationNewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ne pub Fragment"
    }
    val text: LiveData<String> = _text
}