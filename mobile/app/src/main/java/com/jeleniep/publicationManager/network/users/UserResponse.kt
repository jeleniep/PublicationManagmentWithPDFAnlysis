package com.jeleniep.publicationManager.network.users

import com.google.gson.annotations.SerializedName


class UserResponse {
    @SerializedName("email")
    var email: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("profile")
    var profile: String? = null

    @SerializedName("_id")
    var _id: String? = null
}