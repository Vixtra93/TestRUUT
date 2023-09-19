@file:OptIn(ExperimentalMaterial3Api::class)

package com.project.testruut.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.testruut.R
import com.project.testruut.presentation.ui.theme.OrangeLight
import com.project.testruut.presentation.ui.theme.WhiteText

@Composable
fun ProfileScreen(
    onBackPreset: () -> Unit,
    navToLogin: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state = viewModel.state

    if (state.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.my_profile)) },
                    navigationIcon = {
                        IconButton(onClick = onBackPreset) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.userLogout()
                            navToLogin()
                        }, modifier = Modifier.fillMaxWidth(0.2f)) {
                            Text(
                                text = stringResource(R.string.logout),
                                fontWeight = FontWeight.Normal,
                                color = OrangeLight,
                                fontSize = 14.sp,
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            BodyProfile(
                modifier = Modifier.padding(innerPadding), state
            )
        }
    }
}

@Composable
fun BodyProfile(modifier: Modifier, state: ProfileStateUI) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.name_user, state.userProfile?.userName ?: ""),
            color = WhiteText,
            fontWeight = FontWeight.Light,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(R.string.email_field, state.userProfile?.email ?: ""),
            color = WhiteText,
            fontWeight = FontWeight.Light, modifier = Modifier.fillMaxWidth()
        )

    }
}