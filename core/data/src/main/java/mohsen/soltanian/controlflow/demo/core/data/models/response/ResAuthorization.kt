package mohsen.soltanian.controlflow.demo.core.data.models.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ResAuthorization(
    @Json(name = "access_token")
    val accessToken: String?,
    @Json(name = "application_name")
    val applicationName: String?,
    @Json(name = "client_id")
    val clientId: String?,
    @Json(name = "expires_in")
    val expiresIn: Int?,
    @Json(name = "scope")
    val scope: String?,
    @Json(name = "state")
    val state: String?,
    @Json(name = "token_type")
    val tokenType: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "username")
    val username: String?
)