package mohsen.soltanian.controlflow.demo.core.data.providers

import mohsen.soltanian.controlflow.demo.core.data.config.RemoteConfig
import mohsen.soltanian.controlflow.demo.core.data.interceptors.AccessTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

fun provideHttpLoggingInterceptor( config: RemoteConfig): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (!config.isDev()) {
            HttpLoggingInterceptor.Level.NONE
        } else {
            HttpLoggingInterceptor.Level.BODY
        }
    }

fun provideOkHttpClient(config: RemoteConfig
): OkHttpClient {
    val builder = OkHttpClient.Builder().apply {
        addInterceptor(interceptor = provideHttpLoggingInterceptor(config = config))
        connectTimeout(timeout = config.timeOut(),unit= TimeUnit.SECONDS)
        readTimeout(timeout =config.timeOut(),unit= TimeUnit.SECONDS)
        writeTimeout(timeout =config.timeOut(),unit= TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }

    return builder.build()
}

fun provideOkHttpClientWithAmadeusInterceptor(
    config: RemoteConfig, accessTokenInterceptor: AccessTokenInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder().apply {
        addInterceptor(interceptor = provideHttpLoggingInterceptor(config = config))
        addInterceptor(interceptor = accessTokenInterceptor)
        connectTimeout(timeout = config.timeOut(),unit= TimeUnit.SECONDS)
        readTimeout(timeout =config.timeOut(),unit= TimeUnit.SECONDS)
        writeTimeout(timeout =config.timeOut(),unit= TimeUnit.SECONDS)
    }

    return builder.build()
}