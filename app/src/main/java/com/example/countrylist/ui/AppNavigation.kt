package com.example.countrylist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.countrylist.model.CountryListResponseItem
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
/**
   App Navigation Host to move from list to details
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.List.route) {
        composable( Screen.List.route) {   CountryList(navController) }
        composable( Screen.Details.route) {
            val detailArgument = navController.previousBackStackEntry?.savedStateHandle?.get<CountryListResponseItem>(
                "detailArgument"
            )
            if (detailArgument != null) {
                CountryDetails(navController,detailArgument)
            }
        }
    }
}

/**
  Define navigation route here
 */
sealed class Screen(val route:String){
    object List:Screen("List")
    object Details:Screen("Details")
}
