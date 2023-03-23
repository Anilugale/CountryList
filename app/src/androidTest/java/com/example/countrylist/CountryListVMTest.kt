package com.example.countrylist

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.countrylist.model.CountryListResponse
import com.example.countrylist.service.Service
import com.example.countrylist.utility.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/***
 * API Test automation for country List api request
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CountryListVMTest {

    private lateinit var service:Service

    @Before
    fun init() {
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
            .build().create(Service::class.java)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkResponseType() = runTest {
        val countryList = service.getCountryList()
        if(countryList.isSuccessful){
            assert(countryList.body() is CountryListResponse)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkData() = runTest {
        val countryList = service.getCountryList()
        if(countryList.isSuccessful){
           assert(countryList.body()!=null && countryList.body()!!.isEmpty())
        }
    }
}