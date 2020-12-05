package com.jeleniep.publicationManager.interfaces

import com.jeleniep.publicationManager.model.users.UserDTO

interface LoginObserver {
    fun onUserLoginSuccessful(userDTO: UserDTO)
}