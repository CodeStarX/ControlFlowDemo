package mohsen.soltanian.controlflow.demo.screens

import androidx.annotation.Keep
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResHotelData

@Keep
sealed class MainContract {
    @Keep
    sealed class Event {
        @Keep
        object DoAuthorizationAndGetHotels : Event()
    }
    @Keep
    sealed class State {
        @Keep
        data class HotelsData(val hotels: List<ResHotelData>? = listOf()): State()
    }
}
