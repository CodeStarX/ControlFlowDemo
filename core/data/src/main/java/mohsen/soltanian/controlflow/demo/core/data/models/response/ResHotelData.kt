package mohsen.soltanian.controlflow.demo.core.data.models.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ResHotelData(
    @Json(name = "address")
    val address: Address?,
    @Json(name = "chainCode")
    val chainCode: String?,
    @Json(name = "dupeId")
    val dupeId: Long?,
    @Json(name = "geoCode")
    val geoCode: GeoCode?,
    @Json(name = "hotelId")
    val hotelId: String?,
    @Json(name = "iataCode")
    val iataCode: String?,
    @Json(name = "lastUpdate")
    val lastUpdate: String?,
    @Json(name = "name")
    val name: String?
){
    @JsonClass(generateAdapter = true)
    data class Address(
        @Json(name = "countryCode")
        val countryCode: String?
    )

    @JsonClass(generateAdapter = true)
    data class GeoCode(
        @Json(name = "latitude")
        val latitude: Double?,
        @Json(name = "longitude")
        val longitude: Double?
    )
}
