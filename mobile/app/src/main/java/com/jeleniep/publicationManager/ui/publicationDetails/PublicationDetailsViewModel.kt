package com.jeleniep.publicationManager.ui.publicationDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeleniep.publicationManager.interfaces.RequestObserver
import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.model.publications.PublicationRepository
import com.jeleniep.publicationManager.model.publications.PublicationDTO
import com.jeleniep.publicationManager.ui.publicationsList.PublicationListItem


class PublicationDetailsViewModel(private val _id : String?) : ViewModel(), RequestObserver<PublicationDTO> {

    private val _publication: MutableLiveData<PublicationDTO> by lazy {
        val publication = if (_id != null)  PublicationRepository.getPublication(_id!!) else MutableLiveData<PublicationDTO>()
        publication
    }
    val publication: LiveData<PublicationDTO> = _publication

    override fun onSuccess(response: PublicationDTO, type: String) {
        _publication.postValue(response)
    }

    override fun onFail(errorResponse: ErrorResponse?, type: String) {
        TODO("Not yet implemented")
    }

}