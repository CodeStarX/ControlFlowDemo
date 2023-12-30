package mohsen.soltanian.controlflow.demo.core.data.di

import dagger.Module
import mohsen.soltanian.controlflow.demo.core.data.annotations.AuthorizationServicesScope
import mohsen.soltanian.controlflow.demo.core.data.config.RemoteConfig
import mohsen.soltanian.demo.controlflow.data.extensions.moshi
import mohsen.soltanian.controlflow.demo.core.data.network.IAuthorizationService
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohsen.soltanian.controlflow.demo.core.data.annotations.HotelsServicesScope
import mohsen.soltanian.controlflow.demo.core.data.interceptors.AccessTokenInterceptor
import mohsen.soltanian.controlflow.demo.core.data.network.IHotelsServices
import mohsen.soltanian.controlflow.demo.core.data.providers.provideOkHttpClient
import mohsen.soltanian.controlflow.demo.core.data.providers.provideOkHttpClientWithAmadeusInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DIModule {

    @Provides
    @Singleton
    fun provideRemoteConfig(): RemoteConfig = RemoteConfig()

    @Provides
    @Singleton
    @AuthorizationServicesScope
    fun provideAuthorizationService(config: RemoteConfig
    ): IAuthorizationService =
        Retrofit.Builder()
            .client(provideOkHttpClient(config = config))
            .baseUrl(config.baseUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(IAuthorizationService::class.java)


    @Provides
    @Singleton
    @HotelsServicesScope
    fun provideHotelsServices(
        config: RemoteConfig, accessTokenInterceptor: AccessTokenInterceptor
    ): IHotelsServices =
        Retrofit.Builder()
            .client(
                provideOkHttpClientWithAmadeusInterceptor(
                config = config, accessTokenInterceptor = accessTokenInterceptor)
            )
            .baseUrl(config.baseUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(IHotelsServices::class.java)


}