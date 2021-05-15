package com.cotetsu.bison.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            // TODO .baseUrl(BuildConfig.BASE_URL)
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    /**
     * @Provides
     * @Singleton
     * fun provideRetrofitService(retrofit: Retrofit): ${Service} =
     *      retrofit.create({Service}::class.java)
     */
}
