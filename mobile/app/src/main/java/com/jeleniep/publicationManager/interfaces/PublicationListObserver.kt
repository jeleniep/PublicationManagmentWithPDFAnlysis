package com.jeleniep.publicationManager.interfaces

import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.model.publications.PublicationDTO

interface PublicationListObserver {
    fun onPublicationUpdateSuccess(publicationDTO: PublicationDTO, type: String)

    fun onPublicationUpdateFail(errorResponse: ErrorResponse?, type: String)
}