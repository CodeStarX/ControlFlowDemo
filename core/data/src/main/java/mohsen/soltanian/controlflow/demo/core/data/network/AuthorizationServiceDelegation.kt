package mohsen.soltanian.controlflow.demo.core.data.network

import androidx.annotation.Keep
import mohsen.soltanian.controlflow.demo.core.data.annotations.AuthorizationServicesScope
import javax.inject.Inject
import javax.inject.Singleton

@Keep
@Singleton
class AuthorizationServiceDelegation @Inject constructor
    (@AuthorizationServicesScope apiServices: IAuthorizationService) : IAuthorizationService by apiServices