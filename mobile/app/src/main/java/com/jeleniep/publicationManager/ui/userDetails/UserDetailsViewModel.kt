package com.jeleniep.publicationManager.ui.userDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeleniep.PublicationManagerApplication
import com.jeleniep.publicationManager.model.publications.PublicationDTO
import com.jeleniep.publicationManager.model.publications.PublicationRepository
import com.jeleniep.publicationManager.model.users.UserDTO
import com.jeleniep.publicationManager.model.users.UserRepository
import com.jeleniep.publicationManager.utils.SharedPreferencesHelper

class UserDetailsViewModel : ViewModel() {

    private val _user: MutableLiveData<UserDTO> by lazy {
        val user =  UserRepository.getUserDetails("me")
        user
    }
    val user: LiveData<UserDTO> = _user
}