package mohsen.soltanian.controlflow.demo.core.domain.usecases

import kotlinx.coroutines.flow.FlowCollector
import mohsen.soltanian.controlflow.demo.core.data.models.BaseResponse
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResHotelData
import mohsen.soltanian.controlflow.demo.core.data.network.HotelsServiceDelegation
import mohsen.soltanian.controlflow.demo.core.domain.base.BaseUseCase
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHotelsUseCase @Inject constructor(
    private val hotelsServiceDelegation: HotelsServiceDelegation
):
BaseUseCase<BaseUseCase.None, Response<BaseResponse<List<ResHotelData>>>>(){
    override suspend fun FlowCollector<Response<BaseResponse<List<ResHotelData>>>>.execute(params: None) {
        emit(value = hotelsServiceDelegation.hotelsListByCity())

    }
}