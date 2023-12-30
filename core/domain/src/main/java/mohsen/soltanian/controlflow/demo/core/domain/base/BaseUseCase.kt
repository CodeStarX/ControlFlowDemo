package mohsen.soltanian.controlflow.demo.core.domain.base

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in Params, ReturnType> where ReturnType : Any {
    protected abstract suspend fun FlowCollector<ReturnType>.execute(params: Params)
    suspend operator fun invoke(params: Params) = flow {
        execute(params)
    }
    open class None
}