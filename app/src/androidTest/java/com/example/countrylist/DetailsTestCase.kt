package com.example.countrylist

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.countrylist.model.DetailsReq
import com.example.countrylist.model.DetailsResponse
import com.example.countrylist.service.ServiceDetails
import com.example.countrylist.utility.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/***
 * API Test automation for country state api request
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DetailsTestCase {

    @Inject
    lateinit var service:ServiceDetails

    @Before
    fun setup(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val build = OkHttpClient
            .Builder()
            .cache(null)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        service = Retrofit.Builder()
            .baseUrl(Constants.baseUrlForDetails)
            .client(build)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceDetails::class.java)
    }

    @Test
    fun apiConnect() = runTest{
        val countryDetails = service.getCountryDetails(DetailsReq("india"))
        assert(countryDetails.isSuccessful)
    }

    @Test
    fun apiDataCheck() = runTest{
        val countryDetails = service.getCountryDetails(DetailsReq("india"))
        assert(countryDetails.body() is DetailsResponse)
    }
}