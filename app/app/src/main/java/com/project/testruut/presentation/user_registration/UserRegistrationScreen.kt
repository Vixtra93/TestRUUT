@file:OptIn(ExperimentalMaterial3Api::class)

package com.project.testruut.presentation.user_registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.testruut.domain.model.User
import com.project.testruut.presentation.login.EmailField
import com.project.testruut.presentation.login.LoginButton
import com.project.testruut.presentation.login.PasswordField
import com.project.testruut.presentation.ui.theme.WhiteText
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.project.testruut.R
import com.project.testruut.util.Resource
import kotlinx.coroutines.launch
import toast

@Composable
fun UserRegistrationScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserRegistrationViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val stateRegistration by viewModel.stateStatus.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    if (state.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserNameField(state.userName) {
                viewModel.onRegistrationUserChange(
                    it,
                    state.email,
                    state.password
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            EmailField(state.email) {
                viewModel.onRegistrationUserChange(
                    state.userName,
                    it,
                    state.password
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(state.password) {
                viewModel.onRegistrationUserChange(
                    state.userName,
                    state.email,
                    it
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(stringResource(R.string.registration),loginEnable = state.userEnable, onLoginSelected = {
                coroutineScope.launch {
                    viewModel.onRegistrationSelected(
                        User(
                            userName = state.userName,
                            email = state.email,
                            password = state.password
                        ),
                        navigateToLogin = navigateToLogin
                    )
                }
            })

            stateRegistration?.let {
                when (it) {
                    is Resource.Success -> {
                        LocalContext.current.toast(stringResource(R.string.succesful_user_registration))
                    }

                    is Resource.Error -> {
                        LocalContext.current.toast(it.message.toString())

                    }

                    else -> {}
                }
            }
        }

    }

}

@Composable
fun UserNameField(userName: String, onTextFieldChanged: (String) -> Unit) {

    TextField(
        value = userName,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.user_name)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = WhiteText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}