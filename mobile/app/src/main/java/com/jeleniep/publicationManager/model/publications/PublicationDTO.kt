package com.jeleniep.publicationManager.model.publications

import com.google.gson.annotations.SerializedName


class PublicationResponse {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("authors")
    var authors: List<String>? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("_id")
    var _id: String? = null



}