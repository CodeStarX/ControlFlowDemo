package mohsen.soltanian.controlflow.demo.tasks

import io.github.codestarx.helper.Dispatcher
import io.github.codestarx.helper.failureMode
import io.github.codestarx.helper.successMode
import io.github.codestarx.interfaces.TaskProcessor
import io.github.codestarx.models.ConditionData
import io.github.codestarx.models.RetryStrategy
import io.github.codestarx.models.TaskInfo
import io.github.codestarx.status.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import mohsen.soltanian.controlflow.demo.core.domain.base.BaseUseCase
import mohsen.soltanian.controlflow.demo.core.domain.usecases.GetHotelsUseCase
import java.util.concurrent.TimeoutException


class GetHotelsListTask(
    private val useCase: GetHotelsUseCase
): Dispatcher(), TaskProcessor {
    override val info: TaskInfo
        get() = TaskInfo().apply {
            index = 2
            name = GetHotelsListTask::class.java.name
            retry = RetryStrategy().apply {
                count = 3
                causes = setOf(TimeoutException::class)
                delay = 1000L
            }
            runIn = Dispatchers.IO
        }

    override suspend fun doProcess(param: Any?): Flow<TaskStatus> {
        return launchFlow(action = {
            useCase(BaseUseCase.None())
        }, actionCondition = {
            when(it.isSuccessful) {
                true -> {
                    ConditionData(status = Boolean.successMode())
                }
                else -> {
                    ConditionData(status = Boolean.failureMode(), throwable = Throwable("Custom Error Message"))
                }
            }
        })
    }
}