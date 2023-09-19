@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.project.testruut.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.testruut.R
import com.project.testruut.presentation.ui.theme.WhiteText
import com.project.testruut.presentation.ui.theme.OrangeLight
import com.project.testruut.util.Resource
import kotlinx.coroutines.launch

@Composable
fun UserLoginScreen(
    navigateToHome: () -> Unit,
    navigateToUserRegistration: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()

) {
    val state = viewModel.state
    val stateLogin by viewModel.stateLogin.observeAsState()
    val coroutineScope = rememberCoroutineScope()


    if (state.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier.padding(16.dp)) {
            EmailField(state.email) { viewModel.onLoginChange(it, state.password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(state.password) { viewModel.onLoginChange(state.email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            UserRegistration(modifier = Modifier.align(Alignment.End), navigateToUserRegistration)
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(stringResource(id = R.string.login),loginEnable = state.loginEnable, onLoginSelected = {
                coroutineScope.launch {
                    viewModel.onLoginUser(state.email, state.password, navigateToHome)
                }
            })
        }
    }

    stateLogin.let { status ->
        when (status) {
            is Resource.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    status.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
}

@Composable
fun LoginButton(label: String, loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = {
            onLoginSelected()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.White
        ), enabled = loginEnable
    ) {
        Text(label)
    }

}

@Composable
fun UserRegistration(modifier: Modifier, navigateToUserRegistration: () -> Unit) {
    Text(
        text = stringResource(R.string.do_you_have_account),
        modifier = modifier.clickable {
            navigateToUserRegistration()
        },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = OrangeLight

    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_visibility)
    else
        painterResource(id = R.drawable.ic_visibility_off)
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = stringResource(R.string.password)) },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(painter = icon, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility)
            VisualTransformation.None
        else PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = WhiteText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )

    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {

    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.email)) },
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