package mohsen.soltanian.controlflow.demo.screens

import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.codestarx.ControlFlow
import io.github.codestarx.interfaces.RollbackStatusTracker
import io.github.codestarx.interfaces.TaskStatusTracker
import io.github.codestarx.interfaces.WorkFlowTracker
import io.github.codestarx.models.RollbackInfo
import io.github.codestarx.models.TaskFlow
import io.github.codestarx.models.TaskInfo
import io.github.codestarx.status.State
import mohsen.soltanian.controlflow.demo.core.data.models.BaseResponse
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResHotelData
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.manager.SharedPrefCashManager
import mohsen.soltanian.controlflow.demo.core.domain.usecases.AuthorizationUseCase
import mohsen.soltanian.controlflow.demo.core.domain.usecases.GetHotelsUseCase
import mohsen.soltanian.controlflow.demo.core.framework.base.MviViewModel
import mohsen.soltanian.controlflow.demo.core.framework.base.ViewState
import mohsen.soltanian.controlflow.demo.tasks.AuthorizationTask
import mohsen.soltanian.controlflow.demo.tasks.GetHotelsListTask
import mohsen.soltanian.controlflow.demo.tasks.SaveAccessTokenTask
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: AuthorizationUseCase,
    private val getHotelsUseCase: GetHotelsUseCase,
    private val sharedPrefCashManager: SharedPrefCashManager
): MviViewModel<ViewState<MainContract.State>, MainContract.Event>() {

    override fun onTriggerEvent(eventType: MainContract.Event) {
        when(eventType){
            is MainContract.Event.DoAuthorizationAndGetHotels -> {
                executeTasks()
            }
        }
    }
    private fun executeTasks() {
        val engine = ControlFlow(object: WorkFlowTracker {

            override fun started(controlFlow: ControlFlow) {
                Timber.tag("TAG").e("flow Started")
                setState(state = ViewState.Loading)
            }

            override fun taskStatus(controlFlow: ControlFlow, taskFlow: TaskFlow, state: State) {
                when(state) {
                    State.Started -> {
                        when(taskFlow.isRollback) {
                            true -> {
                                Timber.tag("TAG").e("rollback task Started, name is: ${taskFlow.taskName}")
                            }
                            else -> {
                                Timber.tag("TAG").e("task Started, name is: ${taskFlow.taskName}")

                            }
                        }
                    }
                    State.InProgress -> {
                        when(taskFlow.isRollback) {
                            true -> {
                                Timber.tag("TAG").e("rollback task InProgress, name is: ${taskFlow.taskName}")
                            }
                            else -> {
                                Timber.tag("TAG").e("task InProgress, name is: ${taskFlow.taskName}")

                            }
                        }
                    }
                }
            }

            override fun completed(controlFlow: ControlFlow) {
                Timber.tag("TAG").e("flow completed" )
            }

        }).apply {
            startWith(first = AuthorizationTask(useCase = useCase).apply {
                then(subtask= SaveAccessTokenTask(cashManager = sharedPrefCashManager))
            })
            then(next = GetHotelsListTask(useCase = getHotelsUseCase))
        }
        engine.useRollbackStatusTracker(object : RollbackStatusTracker {
            override fun failure(controlFlow: ControlFlow, info: RollbackInfo, errorCause: Throwable?) {
                Timber.tag("TAG").e("An error occurred in rollback task ${info.name}, errorCause is: ${errorCause?.message}")
            }

            override fun successful(controlFlow: ControlFlow, info: RollbackInfo, result: Any?) {
                Timber.tag("TAG").e("rollback task ${info.name} Done Successfully")
            }

        })
        engine.useTaskStatusTracker(object : TaskStatusTracker {
            override fun failure(controlFlow: ControlFlow, info: TaskInfo, errorCause: Throwable?) {
                setState(state = ViewState.Error(throwable = errorCause))
            }

            override fun successful(controlFlow: ControlFlow, info: TaskInfo, result: Any?) {
                when(info.name) {
                    GetHotelsListTask::class.java.name -> {
                        setState(state = ViewState.Data(value = MainContract.State.HotelsData(hotels = (
                                (result as? Response<*>)?.body() as? BaseResponse<List<ResHotelData>>)?.data)))
                    }
                    else -> {
                        Timber.tag("TAG").e("task ${info.name} Done Successfully")
                    }
                }
            }

        })
        engine.start(runAutomaticallyRollback = true)

    }
}