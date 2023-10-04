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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
    val state = viewModel.stateUI

    val stateResource = viewModel.stateResource

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
            UserNameField(state.userName, state.isErrorUserName) {
                viewModel.onRegistrationUserChange(
                    it.trim(),
                    state.email.trim(),
                    state.password.trim()
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            EmailField(state.email, state.isErrorEmail) {
                viewModel.onRegistrationUserChange(
                    state.userName.trim(),
                    it.trim(),
                    state.password.trim(),
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(
                stringResource(R.string.input_login_pass_field),
                state.password,
                state.isErrorPassword
            ) {
                viewModel.onRegistrationUserChange(
                    state.userName.trim(),
                    state.email.trim(),
                    it
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(
                stringResource(R.string.registration),
                loginEnable = state.userEnable,
                onLoginSelected = {
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

            stateResource.resource.let {
                when (it) {
                    is Resource.Success -> {
                        LocalContext.current.toast(stringResource(R.string.succesful_user_registration))
                        stateResource.resource = null
                    }

                    is Resource.Error -> {
                        LocalContext.current.toast(it.message.toString())
                        stateResource.resource = null
                    }

                    else -> {}
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameField(
    userName: String,
    isErrorUserName: Boolean,
    onTextFieldChanged: (String) -> Unit
) {

    TextField(
        value = userName,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.user_name)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        isError = isErrorUserName,
        supportingText = {
            if (isErrorUserName) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.invalid_user_name, userName),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (isErrorUserName)
                Icon(Icons.Default.Warning, "error", tint = MaterialTheme.colorScheme.error)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = WhiteText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}