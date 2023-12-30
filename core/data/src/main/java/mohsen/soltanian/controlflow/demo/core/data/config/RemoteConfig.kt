package mohsen.soltanian.controlflow.demo.core.data.config

import androidx.annotation.Keep
import mohsen.soltanian.controlflow.demo.core.data.BuildConfig
import mohsen.soltanian.controlflow.demo.core.data.enviroment.ApplicationEnvironment
import javax.inject.Inject

@Keep
class RemoteConfig @Inject constructor()   {

    @Keep
    companion object {
        const val GRANT_TYPE = "client_credentials"
        const val API_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        const val API_SECRET = "xxxxxxxxxxxxxxxx"
    }
    private val timeOut: Long = 25

    private fun environment(): ApplicationEnvironment {
        return if (isDev()) {
            ApplicationEnvironment.DEV
        } else {
            ApplicationEnvironment.PUBLIC
        }
    }

    fun baseUrl(): String {
        return when (environment()) {
            ApplicationEnvironment.DEV,  -> {
                BuildConfig.BASE_URL
            }
            ApplicationEnvironment.INTERNAL, ApplicationEnvironment.PUBLIC -> {
                BuildConfig.BASE_URL
            }
        }
    }

    fun timeOut(): Long {
        return timeOut
    }

    fun isDev(): Boolean {
        return true
    }
}