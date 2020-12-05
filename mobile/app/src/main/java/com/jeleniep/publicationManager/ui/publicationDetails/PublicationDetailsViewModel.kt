package com.jeleniep.publicationManager.ui.publicationDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeleniep.publicationManager.model.publications.PublicationRepository
import com.jeleniep.publicationManager.model.publications.PublicationDTO


class PublicationDetailsViewModel(private val _id : String?) : ViewModel() {

    private val _publication: MutableLiveData<PublicationDTO> by lazy {
        val publication = if (_id != null)  PublicationRepository.getPublication(_id!!) else MutableLiveData<PublicationDTO>()
        publication
    }
    val publication: LiveData<PublicationDTO> = _publication

}