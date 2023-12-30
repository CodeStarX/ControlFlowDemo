package mohsen.soltanian.controlflow.demo.core.data.models.request

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ReqAuthorization(
    val grant_type: String = "client_credentials",
    val client_id: String,
    val client_secret: String
)