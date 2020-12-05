package com.jeleniep.publicationManager.model.users

import com.google.gson.annotations.SerializedName


class LoginBody(
    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null
) {}