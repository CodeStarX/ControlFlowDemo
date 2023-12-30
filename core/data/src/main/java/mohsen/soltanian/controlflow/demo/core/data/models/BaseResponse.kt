package mohsen.soltanian.controlflow.demo.core.data.models

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "data")
    val data: T?,
    @Json(name = "meta")
    val meta: Meta
) {

    @Keep
    @JsonClass(generateAdapter = true)
    data class Meta(
      @Json(name = "count")
      val count: Long,
      @Json(name = "links")
      val links: Links?
    ){

        @Keep
        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "self")
            val self: String?
        )
    }
}