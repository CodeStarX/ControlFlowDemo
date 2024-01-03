package mohsen.soltanian.controlflow.demo.tasks

import io.github.codestarx.helper.Dispatcher
import io.github.codestarx.helper.HttpException
import io.github.codestarx.helper.failureMode
import io.github.codestarx.helper.successMode
import io.github.codestarx.interfaces.TaskProcessor
import io.github.codestarx.models.ConditionData
import io.github.codestarx.models.RetryStrategy
import io.github.codestarx.models.TaskInfo
import io.github.codestarx.models.TransformData
import io.github.codestarx.status.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResAuthorization
import mohsen.soltanian.controlflow.demo.core.domain.base.BaseUseCase
import mohsen.soltanian.controlflow.demo.core.domain.usecases.AuthorizationUseCase
import mohsen.soltanian.demo.controlflow.data.extensions.fromJson
import mohsen.soltanian.demo.controlflow.data.extensions.toJson
import org.json.JSONObject
import java.util.concurrent.TimeoutException

class AuthorizationTask(
    private val useCase: AuthorizationUseCase
) : Dispatcher(), TaskProcessor {
    override val info: TaskInfo
        get() = TaskInfo().apply {
            index = 0
            name = AuthorizationTask::class.java.name
            retry = RetryStrategy().apply {
                count = 3
                causes = setOf(TimeoutException::class)
                delay = 1000L
            }
            runIn = Dispatchers.IO
        }

    override suspend fun doProcess(param: Any?): Flow<TaskStatus> {
        return launchFlow(action =  { useCase(params = BaseUseCase.None()) },
             transformer = { TransformData(data = it.body().toJson().fromJson<ResAuthorization>()?.accessToken) },
             actionCondition = {
                    when(it.isSuccessful) {
                        true -> {
                            ConditionData(status = Boolean.successMode())
                        }
                        else -> {
                            // pars error
                            val jObjError: JSONObject? =
                                it.errorBody()?.string()
                                    ?.let { it1 -> JSONObject(it1) }
                            var errorDescription = ""
                            var errorCode: Int = -1
                            if(jObjError?.has("error_description") == true && jObjError.has("code")) {
                                errorDescription = jObjError.getString("error_description")
                                errorCode = jObjError.getInt("code")
                            }
                            ConditionData(status = Boolean.failureMode(), throwable = HttpException(code = errorCode, message = errorDescription))
                        }
                    }
        })
    }
}