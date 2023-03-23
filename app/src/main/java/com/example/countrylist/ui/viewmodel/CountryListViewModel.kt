package com.example.countrylist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylist.model.CountryListResponseItem
import com.example.countrylist.service.Service
import com.example.countrylist.utility.ConnectivityInterceptor
import com.example.countrylist.utility.ConnectivityInterceptorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
   View model which handling request and response and updated screen state
 */
@HiltViewModel
class CountryListViewModel @Inject constructor(
    val service: Service,
    private val connection: ConnectivityInterceptor
) : ViewModel() {
    private var state: MutableStateFlow<CountryListState> =
        MutableStateFlow(CountryListState.Progress)
    val stateExpose = state.asStateFlow()

    init {
      getCountryList()
    }

    fun getCountryList() {
        viewModelScope.launch {
            if (connection.isOnline()) {
                try {
                    val response = service.getCountryList()
                    delay(3000)
                    if (response.isSuccessful && response.body() != null) {
                        state.value = CountryListState.Success(response.body()!!)
                    } else {
                        state.value = CountryListState.Error(response.message())
                    }
                } catch (ignore: Exception) {
                    state.value = CountryListState.Error(ignore.message + "")
                }
            } else {
                state.value = CountryListState.NoInternet
            }

        }
    }
}

/**
   Define state of screen for list
 */
sealed class CountryListState {
    class Error(val msg: String) : CountryListState()
    object Progress : CountryListState()
    object NoInternet : CountryListState()
    class Success(val list: ArrayList<CountryListResponseItem>) : CountryListState()
}