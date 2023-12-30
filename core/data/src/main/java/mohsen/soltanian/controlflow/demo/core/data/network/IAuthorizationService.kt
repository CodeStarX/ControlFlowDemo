package mohsen.soltanian.controlflow.demo.core.data.network

import androidx.annotation.Keep
import mohsen.soltanian.controlflow.demo.core.data.models.response.ResAuthorization
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@Keep
interface IAuthorizationService {
    @FormUrlEncoded
    @POST("v1/security/oauth2/token")
    suspend fun authorization(@Field("grant_type") grantType: String,@Field("client_id") clientId: String,
                              @Field("client_secret") clientSecret: String) : Response<ResAuthorization>
}