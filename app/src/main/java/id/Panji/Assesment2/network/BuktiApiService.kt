package id.Panji.Assesment2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import id.Panji.Assesment2.model.Bukti
import id.Panji.Assesment2.model.OpStatus
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://api.panjipradipa.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BuktiApiService {
    @GET("json.php")
    suspend fun getBukti(
        @Query("auth") userId: String
    ): List<Bukti>

    @Multipart
    @POST("json.php")
    suspend fun postBukti(
        @Part("auth") userId: String,
        @Part("kategori") kategori: RequestBody,
        @Part("nominal") nominal: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("json.php")
    suspend fun deleteBukti(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object BuktiApi {
    val service: BuktiApiService by lazy {
        retrofit.create(BuktiApiService::class.java)
    }
    fun getBuktiUrl(gambar: String): String {
        return "$BASE_URL$gambar"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }