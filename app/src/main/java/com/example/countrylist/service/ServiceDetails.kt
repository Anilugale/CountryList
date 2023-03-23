package com.example.countrylist.service

import com.example.countrylist.model.DetailsReq
import com.example.countrylist.model.DetailsResponse
import com.example.countrylist.utility.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
/***
   Service that make network req for country details
 */

interface ServiceDetails {
    @POST(Constants.detailsApi)
    suspend fun getCountryDetails(@Body detailsReq: DetailsReq): Response<DetailsResponse>
}