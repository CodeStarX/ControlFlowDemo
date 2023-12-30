package mohsen.soltanian.controlflow.demo.core.data.network

import androidx.annotation.Keep
import mohsen.soltanian.controlflow.demo.core.data.models.BaseResponse
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResHotelData
import retrofit2.Response
import retrofit2.http.GET

@Keep
interface IHotelsServices {
    @GET("v1/reference-data/locations/hotels/by-city?cityCode=PAR")
    suspend fun hotelsListByCity(): Response<BaseResponse<List<ResHotelData>>>
}