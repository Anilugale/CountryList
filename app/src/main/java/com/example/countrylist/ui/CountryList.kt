package com.example.countrylist.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.countrylist.R
import com.example.countrylist.model.CountryListResponseItem
import com.example.countrylist.ui.viewmodel.CountryListState
import com.example.countrylist.ui.viewmodel.CountryListViewModel
import com.valentinilk.shimmer.shimmer
import java.util.ArrayList


/** Updated ui state as per the view model state update */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryList(navController: NavHostController) {
    val vmModel: CountryListViewModel = hiltViewModel()
    val viewModel = remember {
        vmModel
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Country List") },
                scrollBehavior = scrollBehavior,

                navigationIcon = {
                    Icon(Icons.Default.Menu, contentDescription = "", modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            navController.navigateUp()
                        })

                },

                )
        }
    ) {
        when (val value = viewModel.stateExpose.collectAsState().value) {
            CountryListState.Progress -> {
                ShowListProgress(it)
            }
            CountryListState.NoInternet -> {
                NoInternet()
            }

            is CountryListState.Success -> {
                ShowCountryList(value.list, it, navController)
            }

            is CountryListState.Error -> {
                Log.d("CountryDetails", "CountryDetails: ${value.msg}")
                ServerError()
            }

        }
    }
}

/** UI showing progress animation and shimmer effect */
@Composable
fun ShowListProgress(paddingValues: PaddingValues) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {
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

            ){

            }
        }
    }
}


/** UI showing List of country */
@Composable
fun ShowCountryList(
    list: ArrayList<CountryListResponseItem>,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    LazyColumn(contentPadding = paddingValues) {
        items(count = list.size, key = { it }) {
            ShowCountryItem(list[it], navController)
        }
    }
}

/** UI of single country list items */
@Composable
fun ShowCountryItem(it: CountryListResponseItem, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("detailArgument", it)
                }

                navController.navigate(Screen.Details.route)
            },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = Modifier.padding(10.dp).width(80.dp).height(50.dp),
                model = it.flags.png,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder_1),
                contentScale = ContentScale.FillBounds
            )

            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = it.name.common,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = it.name.official,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

/** UI preview for the progress animation screen */
@Preview
@Composable
fun ShowProgressListPre() {
    ShowListProgress(PaddingValues(10.dp))
}
