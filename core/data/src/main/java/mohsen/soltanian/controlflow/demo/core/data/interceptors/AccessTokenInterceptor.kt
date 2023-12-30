package mohsen.soltanian.controlflow.demo.core.data.interceptors

import mohsen.soltanian.demo.controlflow.data.extensions.empty
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.manager.SharedPrefCashManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenInterceptor @Inject constructor(
    private val cashManager: SharedPrefCashManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val interceptorRequest: Request = request.newBuilder()
            .addHeader("Authorization", "Bearer ${cashManager.findPreference(key = ACCESS_TOKEN, String.empty())}")
            .build()
        return chain.proceed(interceptorRequest)

    }

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }
}