package com.jeleniep.publicationManager.network.users

import com.google.gson.annotations.SerializedName


class LoginBody(
    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null
) {}