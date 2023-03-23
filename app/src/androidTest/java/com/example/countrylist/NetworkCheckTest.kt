package com.example.countrylist

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.countrylist.model.DetailsReq
import com.example.countrylist.model.DetailsResponse
import com.example.countrylist.service.ServiceDetails
import com.example.countrylist.utility.ConnectivityInterceptor
import com.example.countrylist.utility.ConnectivityInterceptorImpl
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
 * Check Network check utility class ConnectivityInterceptor
 */
@RunWith(AndroidJUnit4::class)
class NetworkCheckTest {

    @Inject
    lateinit var service: ConnectivityInterceptor

    @Before
    fun setup(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        service =  ConnectivityInterceptorImpl(appContext)
    }

    @Test
    fun checkNetwork() {
        assert(service.isOnline()) { " Device is Online " }
    }
}