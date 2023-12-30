package mohsen.soltanian.controlflow.demo.core.data.network

import androidx.annotation.Keep
import mohsen.soltanian.controlflow.demo.core.data.annotations.HotelsServicesScope
import javax.inject.Inject
import javax.inject.Singleton

@Keep
@Singleton
class HotelsServiceDelegation @Inject constructor
    (@HotelsServicesScope apiServices: IHotelsServices) : IHotelsServices by apiServices