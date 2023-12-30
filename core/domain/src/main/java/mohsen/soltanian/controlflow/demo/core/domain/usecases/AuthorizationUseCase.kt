package mohsen.soltanian.controlflow.demo.core.domain.usecases

import kotlinx.coroutines.flow.FlowCollector
import mohsen.soltanian.controlflow.demo.core.data.config.RemoteConfig.Companion.API_KEY
import mohsen.soltanian.controlflow.demo.core.data.config.RemoteConfig.Companion.API_SECRET
import mohsen.soltanian.controlflow.demo.core.data.config.RemoteConfig.Companion.GRANT_TYPE
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResAuthorization
import mohsen.soltanian.controlflow.demo.core.data.network.AuthorizationServiceDelegation
import mohsen.soltanian.controlflow.demo.core.domain.base.BaseUseCase
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationUseCase @Inject constructor(
    private val servicesDelegation: AuthorizationServiceDelegation
):
BaseUseCase<BaseUseCase.None, Response<ResAuthorization>>(){

    override suspend fun FlowCollector<Response<ResAuthorization>>.execute(params: None) {
        emit(value = servicesDelegation.authorization(grantType = GRANT_TYPE,clientId = API_KEY, clientSecret = API_SECRET))
    }
}