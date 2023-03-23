package com.example.countrylist

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.example.countrylist.service.Service
import com.example.countrylist.ui.viewmodel.CountryListState
import com.example.countrylist.ui.viewmodel.CountryListViewModel
import com.example.countrylist.utility.ConnectivityInterceptorImpl
import com.example.countrylist.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/***
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var viewModel: CountryListViewModel

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val connection = ConnectivityInterceptorImpl(context.applicationContext)


        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val build = OkHttpClient
            .Builder()
            .cache(null)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val service = Retrofit.Builder()
            .baseUrl(Constants.baseUrlForDetails)
            .client(build)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Service::class.java)

        viewModel = CountryListViewModel(service, connection)
    }

    @Test
    fun doSomeTest() {
        viewModel.getCountryList()
        Log.d("s", "doSomeTest: ${viewModel.stateExpose.value}")
        assert(viewModel.stateExpose.value is CountryListState.Success)
    }
}