# Control Flow Demo
In this project, I have used clean architecture with a VMI pattern.

# Libraries included in this project
[`control-flow`](https://github.com/CodeStarX/ControlFlow) `jetpack compose` `kotlin coroutine` `coroutine flow` `retrofit` `okhttp` `view-model` `hilt` `timber`

# Modules
[`:core:data`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/core/data/src/main/java/mohsen/soltanian/controlflow/demo/core/data/) It includes classes that are used to communicate with the server.

[`:core:domain`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/core/domain/src/main/java/mohsen/soltanian/controlflow/demo/core/domain/) It includes use case classes between data module and presentation module.

[`:core:framework`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/core/framework/src/main/java/mohsen/soltanian/controlflow/demo/core/framework/) It includes the classes used in the presentation module.

[`:presentation`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/presentation/src/main/java/mohsen/soltanian/controlflow/demo/) It includes the ui to interact with the user and also contains the viewModel class.

# Project Structure

In this project, the [Amadeus website](https://developers.amadeus.com/) services facilitate obtaining a list of hotels, involving a series of three essential tasks:

Note: Please utilize your unique `API_KEY` and `API_SECRET`, as mine are concealed. Once obtained, insert them into the [RemoteConfig Class](https://github.com/CodeStarX/ControlFlowDemo/tree/master/core/data/src/main/java/mohsen/soltanian/controlflow/demo/core/data/config/RemoteConfig.kt)

`API_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"`

`API_SECRET = "xxxxxxxxxxxxxxxx"`

1. Invoking the `authorization` web service to procure the crucial `access_token`
2. Safeguarding the obtained `access_token` by storing it through the `SharedPreferences` functionality.
3. Retrieving the catalog of hotels by utilizing the `hotelsListByCity` web service.

To orchestrate these asynchronous tasks effectively, the Control-Flow library assumes the responsibility of task management. In the provided code samples:

- The [`AuthorizationTask`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/presentation/src/main/java/mohsen/soltanian/controlflow/demo/tasks/AuthorizationTask.kt/) class takes charge of the initial task.
- Subsequently, the [`SaveAccessTokenTask`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/presentation/src/main/java/mohsen/soltanian/controlflow/demo/tasks/SaveAccessTokenTask.kt/)  class handles the second task.
- Finally, the [`GetHotelsListTask`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/presentation/src/main/java/mohsen/soltanian/controlflow/demo/tasks/GetHotelsListTask.kt/) class executes the third task.

```kotlin
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
            startWith(first = AuthorizationTask(useCase = useCase))
            then(next = SaveAccessTokenTask(cashManager = sharedPrefCashManager))
            then(next = GetHotelsListTask(useCase = getHotelsUseCase))
        }
```

Note: To activate the Retry mechanism, I've configured the properties of the [`AuthorizationTask`](https://github.com/CodeStarX/ControlFlowDemo/tree/master/presentation/src/main/java/mohsen/soltanian/controlflow/demo/tasks/AuthorizationTask.kt/) class.
In case of a `TimeoutException` error, the task will be retried thrice, with a one-second interval between each attempt.

```kotlin
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
        ...
    }
}
```


Note: the output of the first task undergoes conversion via the `transformer` method before being passed to the second task.

```kotlin
class AuthorizationTask(
    private val useCase: AuthorizationUseCase
) : Dispatcher(), TaskProcessor {
    override val info: TaskInfo
        get() = ...

    override suspend fun doProcess(param: Any?): Flow<TaskStatus> {
        return launchFlow(action =  { useCase(params = BaseUseCase.None()) },
             transformer = { TransformData(data = it.body().toJson().fromJson<ResAuthorization>()?.accessToken) },
             actionCondition = {
                    when(it.isSuccessful) {
                        true -> {
                            ConditionData(status = Boolean.successMode())
                        }
                        else -> {
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
```

Note: If an error occurs while calling the `hotelsListByCity` web service, the `doRollbackProcess` method run automatically. It effectively manages the removal of the previously acquired `access-token`, playing a crucial role in evaluating the rollback function.

```kotlin
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
```

To activate the rollback mechanism automatically after any task encounters an error, ensure to set the `runAutomaticallyRollback` flag to `true` within the `start` method. By default, the value of this flag is `runAutomaticallyRollback= false`

```kotlin
 // Create a ControlFlow instance
val controlFlow = ControlFlow(object : WorkFlowTracker {
// Implement work Flow callback methods
})

// Define your tasks
controlFlow.startWith(MyTask())
controlFlow.then(AnotherTask())
controlFlow.then(AnotherTask())

// Set up TaskStatusTracker if needed
controlFlow.useTaskStatusTracker(object : TaskStatusTracker {
// Implement callback methods
})

// Set up RollbackStatusTracker if needed
controlFlow.useRollbackStatusTracker(object : RollbackStatusTracker {
// Implement callback methods
})

// Start executing tasks
controlFlow.start(runAutomaticallyRollback= true)
```

## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request.
Remember to show your support by giving the project a star. Thank you once more  :)

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/YourFeatureName`)
3. Commit your Changes (`git commit -m 'Add some YourFeatureName'`)
4. Push to the Branch (`git push origin feature/YourFeatureName`)
5. Open a Pull Request


You can contact me via email `soltaniyan.mohsen@gmail.com`
I appreciate your interest.

## Screenshots
| <img src="https://i.imgur.com/VTdS0JP.jpg" width="250">  | <img src="https://i.imgur.com/vVvSxb6.jpg" width="250"> 


# License

MIT License

Copyright (c) 2023 Related Code

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.



