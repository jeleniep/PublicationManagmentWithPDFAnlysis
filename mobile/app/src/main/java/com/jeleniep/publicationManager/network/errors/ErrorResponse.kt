package com.jeleniep.publicationManager.network.errors

import com.google.gson.annotations.SerializedName

class ErrorResponse {
    @SerializedName("code")
    var code: String? = null

    @SerializedName("message")
    var message: String? = null
}