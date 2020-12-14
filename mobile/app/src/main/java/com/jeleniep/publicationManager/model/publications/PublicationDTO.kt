package com.jeleniep.publicationManager.model.publications

import com.google.gson.annotations.SerializedName


class PublicationDTO {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("authors")
    var authors: List<String>? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("_id")
    var _id: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("file")
    var file: String? = null


}