package com.jeleniep.publicationManager.interfaces

import com.jeleniep.publicationManager.network.errors.ErrorResponse

interface RequestObserver<T> {

    fun onSuccess(response: T, type: String)


    fun onFail(errorResponse: ErrorResponse?, type: String)

}