
import com.google.gson.annotations.SerializedName


class PublicationRepository {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("authors")
    var authors: List<String>? = null

    @SerializedName("_id")
    var _id: String? = null


}