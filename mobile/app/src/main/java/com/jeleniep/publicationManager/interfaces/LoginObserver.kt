package com.jeleniep.publicationManager.interfaces

import com.jeleniep.publicationManager.network.users.UserDTO

interface LoginObserver {
    fun onUserLoginSuccessful(userDTO: UserDTO)
}