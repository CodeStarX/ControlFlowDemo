package mohsen.soltanian.controlflow.demo.core.framework.base

import androidx.annotation.Keep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Keep
abstract class MviViewModel<STATE : ViewState<*>, EVENT> : BaseViewModel() {

    private val _uiState = MutableStateFlow<ViewState<*>>(ViewState.Empty)
    val uiState = _uiState.asStateFlow()

    abstract fun onTriggerEvent(eventType: EVENT)

    protected fun setState(state: STATE) = safeLaunch {
        _uiState.emit(state)
    }

    override fun startLoading() {
        super.startLoading()
        _uiState.value = ViewState.Loading
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        _uiState.value = ViewState.Error(exception)
    }
}