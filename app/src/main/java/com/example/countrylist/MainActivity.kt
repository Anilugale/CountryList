package com.example.countrylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.countrylist.ui.AppNavigation
import com.example.countrylist.ui.theme.CountryListTheme
import dagger.hilt.android.AndroidEntryPoint

/** Single Activity Class for main Screen show */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryListTheme {
                AppNavigation()
            }
        }
    }
}


