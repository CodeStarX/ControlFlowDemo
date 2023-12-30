package mohsen.soltanian.controlflow.demo.core.framework.base

import androidx.annotation.Keep

@Keep
sealed interface ViewState<out T> {
    @Keep
    object InitState : ViewState<Nothing>
    @Keep
    object Loading : ViewState<Nothing>
    @Keep
    object Empty : ViewState<Nothing>
    @Keep
    data class Data<T>(val value: T) : ViewState<T>
    @Keep
    data class Error(val throwable: Throwable?) : ViewState<Nothing>
}