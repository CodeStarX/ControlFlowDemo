package mohsen.soltanian.controlflow.demo.tasks

import io.github.codestarx.helper.Dispatcher
import io.github.codestarx.interfaces.RollbackTaskProcessor
import io.github.codestarx.models.RollbackInfo
import io.github.codestarx.models.TaskInfo
import io.github.codestarx.status.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import mohsen.soltanian.controlflow.demo.core.data.interceptors.AccessTokenInterceptor.Companion.ACCESS_TOKEN
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.manager.SharedPrefCashManager
import mohsen.soltanian.demo.controlflow.data.extensions.empty

class SaveAccessTokenTask(
    private val cashManager: SharedPrefCashManager
): Dispatcher(), RollbackTaskProcessor {
    override val info: TaskInfo
        get() = TaskInfo().apply {
            index = 1
            name = SaveAccessTokenTask::class.java.name
            runIn = Dispatchers.IO
        }
    override val rollbackInfo: RollbackInfo
        get() = RollbackInfo().apply {
            index = 1
            name = SaveAccessTokenTask::class.java.name
            runIn = Dispatchers.IO
        }

    override suspend fun doProcess(param: Any?): Flow<TaskStatus> {
        return launch { cashManager.putPreference(key = ACCESS_TOKEN, value = param) }
    }

    override suspend fun doRollbackProcess(): Flow<TaskStatus> {
        return launch { cashManager.putPreference(key = ACCESS_TOKEN, value = String.empty()) }
    }
}