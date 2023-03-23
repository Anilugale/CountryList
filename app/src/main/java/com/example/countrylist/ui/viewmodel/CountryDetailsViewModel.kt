package com.example.countrylist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylist.model.DetailsReq
import com.example.countrylist.model.DetailsResponse
import com.example.countrylist.service.ServiceDetails
import com.example.countrylist.utility.ConnectivityInterceptor
import com.example.countrylist.utility.ConnectivityInterceptorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
   View model which handling request and response and updated screen state
 */
@HiltViewModel
class CountryDetailsViewModel @Inject constructor(private val service:ServiceDetails,private val connection: ConnectivityInterceptor):ViewModel() {
    private var state: MutableStateFlow<CountryDetailsState> = MutableStateFlow(CountryDetailsState.Progress)
    val stateExpose = state.asStateFlow()

    fun getDetails(country:String){
        if (connection.isOnline()) {
            try {
            viewModelScope.launch {
                val response = service.getCountryDetails(DetailsReq(country))
                if (response.isSuccessful && response.body()!=null) {
                    state.value = CountryDetailsState.Success(response.body()!!)
                }else{
                    state.value = CountryDetailsState.Error(response.message())
                }
            }
            }catch (ignore:Exception){
                state.value = CountryDetailsState.Error(ignore.message+"")
            }
        }else{
            state.value = CountryDetailsState.NoInternet
        }

    }
}

/**
   Define state of screen for Details
 */
sealed class CountryDetailsState{
    class Error(val msg:String) : CountryDetailsState()
    object Progress : CountryDetailsState()
    object NoInternet : CountryDetailsState()
    class Success (val model : DetailsResponse): CountryDetailsState()
}