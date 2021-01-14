package com.jeleniep.publicationManager.ui.userDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeleniep.publicationManager.network.users.UserDTO
import com.jeleniep.publicationManager.network.users.UserRepository

class UserDetailsViewModel : ViewModel() {

    private val _user: MutableLiveData<UserDTO> by lazy {
        val user =  UserRepository.getUserDetails("me")
        user
    }
    val user: LiveData<UserDTO> = _user
}