package com.example.countrylist.service

import com.example.countrylist.model.CountryListResponse
import com.example.countrylist.utility.Constants
import retrofit2.Response
import retrofit2.http.GET

/***
   Service that make network req for country list
 */
interface Service {
    @GET(Constants.listApi)
    suspend fun getCountryList(): Response<CountryListResponse>
}