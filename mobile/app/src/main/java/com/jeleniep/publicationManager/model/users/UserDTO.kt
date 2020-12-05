package com.jeleniep.publicationManager.model.users

import com.google.gson.annotations.SerializedName


class LoginDTO {
    @SerializedName("email")
    var email: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("profile")
    var profile: String? = null

    @SerializedName("_id")
    var _id: String? = null

    @SerializedName("authToken")
    var authToken: String? = null


}