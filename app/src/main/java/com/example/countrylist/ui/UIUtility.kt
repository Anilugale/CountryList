package com.example.countrylist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

/**
  Common Ui Class define common UI Design
 */


/** No Internet Lottie Animation */
@Composable
fun NoInternet() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("disconnect.json"))
    Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
        LottieAnimation(composition, modifier = Modifier.size(250.dp))
    }
}

/** No Data Lottie Animation */
@Composable
fun NoData() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("nodata.json"))
    Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
        LottieAnimation(composition, modifier = Modifier.size(250.dp))
    }
}

/** Server Error Lottie Animation */
@Composable
fun ServerError() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("error_404.json"))
    Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
        LottieAnimation(composition, modifier = Modifier.size(250.dp))
    }
}