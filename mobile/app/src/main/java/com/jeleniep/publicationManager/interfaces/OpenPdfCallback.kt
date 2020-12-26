package com.jeleniep.publicationManager.interfaces

import com.jeleniep.publicationManager.model.users.UserDTO

interface OpenPdfCallback {
    fun onPdfDownloaded(path: String)
}