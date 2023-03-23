package com.example.countrylist.nw

import android.app.Application
import com.example.countrylist.service.Service
import com.example.countrylist.service.ServiceDetails
import com.example.countrylist.utility.ConnectivityInterceptor
import com.example.countrylist.utility.ConnectivityInterceptorImpl
import com.example.countrylist.utility.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
/***
   Dependency Provider like okhttps and network check
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .cache(null)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providerService(retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)


    @Singleton
    @Provides
    fun provideServiceDetails(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): ServiceDetails =
        Retrofit.Builder()
            .baseUrl(Constants.baseUrlForDetails)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build().create(ServiceDetails::class.java)


    @Singleton
    @Provides
    fun providesConnectivityInterceptorImpl(application: Application):
            ConnectivityInterceptor {
        return ConnectivityInterceptorImpl(application)
    }
}