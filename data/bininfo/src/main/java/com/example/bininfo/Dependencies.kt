package com.example.bininfo

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BinInfoModule {
    init {
        Log.e("BinInfoModule created", "")
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    //RDS - remote data source
    @Provides
    @Singleton
    fun provideBinInfoRds(
        @ApplicationContext applicationContext: Context,
        gson: Gson
    ): BinlistApi {
        val hostSource = "https://lookup.binlist.net"
        val retrofitBuilder = Retrofit
            .Builder()
            .baseUrl(hostSource)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofitBuilder.create(BinlistApi::class.java)
    }
}

interface BinlistApi {
    @GET("/{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinlistResponse
}

data class BinlistResponse(
    @SerializedName("scheme") val paymentSystem: String,
    @SerializedName("type") val cardType: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("prepaid") val isPrepaidCard: Boolean,
    val country: CountryInfo,
    val bank: BankInfo
)

data class CountryInfo(
    val name: String,
    val latitude: String,
    val longitude: String
)

data class BankInfo(
    val name: String,
    val url: String,
    val phone: String,
    val city: String
)

