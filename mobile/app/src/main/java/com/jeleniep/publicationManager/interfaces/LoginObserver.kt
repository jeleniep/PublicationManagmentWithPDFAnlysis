package com.jeleniep.publicationManager.interfaces

import com.jeleniep.publicationManager.network.errors.ErrorResponse
import com.jeleniep.publicationManager.network.users.UserDTO

interface LoginObserver {
    fun onUserLoginSuccessful(userDTO: UserDTO)
    fun onUserLoginFailure(errorResponse: ErrorResponse?)
}