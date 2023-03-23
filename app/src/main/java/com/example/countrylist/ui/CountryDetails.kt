package com.example.countrylist.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.countrylist.R
import com.example.countrylist.model.CountryListResponseItem
import com.example.countrylist.ui.viewmodel.CountryDetailsState
import com.example.countrylist.ui.viewmodel.CountryDetailsViewModel
import com.valentinilk.shimmer.shimmer


/** Updated ui state as per the view model state update */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetails(navController: NavHostController, countryList: CountryListResponseItem) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val vmModel: CountryDetailsViewModel = hiltViewModel()
    val viewModel = remember {
        vmModel
    }
  LaunchedEffect(key1 = true){
      vmModel.getDetails(countryList.name.common)
  }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text =  countryList.name.common, fontSize = 16.sp) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
            )
        }
    ) { padding ->
        val value = viewModel.stateExpose.collectAsState().value
        LazyColumn(modifier = Modifier.padding(padding)){
            item {
                ShowHeader(countryList)
            }
            when (value) {
                is CountryDetailsState.Error -> {
                    Log.d("CountryDetails", "CountryDetails: ${value.msg}")
                    item {
                        ServerError()
                    }
                }
                CountryDetailsState.Progress -> {
                    items(count = 8) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .height(80.dp)
                                .shimmer()
                                .background(
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(5.dp)
                                )
                        )
                    }
                }
                CountryDetailsState.NoInternet->{
                    item {
                        NoInternet()
                    }
                }
                is CountryDetailsState.Success -> {
                    val states = value.model.data.states
                    if (states.isEmpty()) {
                        item {  NoData()}

                    }else {

                        item {
                            Text(text = "State List", modifier = Modifier.padding(horizontal = 10.dp))
                        }
                        items(count = states.size, key = { states[it].name }) {index->
                            val state = states[index]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                shape = RoundedCornerShape(3.dp)

                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "Name : ${state.name}")
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(text = "State code : ${state.state_code}", fontSize = 14.sp)
                                }

                            }
                        }
                    }
                }
            }

        }

    }
}

/** UI showing selected country as header */
@Composable
fun ShowHeader(countryList: CountryListResponseItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                modifier = Modifier.padding(10.dp),
                model = countryList.flags.png,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder_1)
            )
            Text(
                text = countryList.name.official,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
