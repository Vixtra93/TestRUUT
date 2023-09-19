@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.project.testruut.presentation.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.project.testruut.R


@Composable
fun CompanyListingsScreen(
    viewModel: CompanyListingViewModel = hiltViewModel(),
    navToProfile: () -> Unit,
    navToLogin: () -> Unit
) {

    LaunchedEffect(key1 = Unit, block = {

        viewModel.isValidAutentication(navToLogin)

    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.test_ruut)) },
                actions = {
                    IconButton(onClick = {
                        navToProfile()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = stringResource(R.string.profile)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyListing(
            modifier = Modifier.padding(innerPadding),
            viewModel
        )
    }

}

@Composable
fun BodyListing(
    modifier: Modifier,
    viewModel: CompanyListingViewModel
) {

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(CompanyListingsEvent.Refresh)
            }) {
            ListItems(state = state)
        }
    }

}


@Composable
fun ListItems(state: CompanyListingStateUI) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(state.companies.size) { i ->
            val company = state.companies[i]
            CompanyItem(
                company = company, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            if (i < state.companies.size) {
                Divider(
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }

        }
    }
}