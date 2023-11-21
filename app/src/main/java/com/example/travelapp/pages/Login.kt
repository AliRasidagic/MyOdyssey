package com.example.travelapp.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.travelapp.Navigation
import com.example.travelapp.R
import com.example.travelapp.view_models.RegistrationLoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    viewModel: RegistrationLoginViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val uiState = viewModel.state
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val loginButton: () -> Unit = {
        navController.navigate(Navigation.Profile.name)
    }

    val loginButtonClick: () -> Unit = {
        if (uiState.email.isEmpty() || uiState.password.isEmpty()) {
            errorMessage = "Please fill in all fields"
        } else {
            viewModel.viewModelScope.launch {
                val existingUser = viewModel.checkUserByEmailLogin(uiState.email)
                if (existingUser == null) {
                    errorMessage = "User does not exist, please register!"
                } else {
                    if (existingUser.password != uiState.password) {
                        errorMessage = "Incorrect password, please try again!"
                    } else {
                        navController.navigate(Navigation.Profile.name)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(color = Black)
            .fillMaxSize()
            .clickable { keyboardController?.hide(); focusManager.clearFocus() }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 60.dp)
            )
            TextField(
                value = uiState.email,
                onValueChange = { text ->
                    viewModel.onEmailChange(text)
                },
                label = {
                    Text(
                        "Email",
                        color = Black
                    )
                },
                modifier = Modifier
                    .padding(top = 145.dp)
                    .widthIn(300.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(text = "E-mail")
                },
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = White,
                    textColor = Black
                )
            )
            TextField(
                value = uiState.password,
                onValueChange = { text ->
                    viewModel.onPasswordChange(text)
                },
                label = {
                    Text(
                        "Password",
                        color = Black
                    )
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .widthIn(300.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                },
                trailingIcon = {
                    val visibilityIcon =
                        if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        content = {
                            Icon(
                                imageVector = visibilityIcon,
                                contentDescription = null,
                                tint = Black
                            )
                        }
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    loginButtonClick()
                    keyboardController?.hide()
                }),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = White,
                    textColor = Black
                )
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.background(Black),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.5.dp, color = White)
                ) {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(if (checked) Black else White)
                            .clickable {
                                checked = !checked
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if(checked)
                            Icon(Icons.Default.Check, contentDescription = "", tint = White)
                    }
                }
                Text(
                    text = "Remember me",
                    color = White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(CenterVertically)
                )
            }
            OutlinedButton(
                onClick = loginButton,
                colors = ButtonDefaults.buttonColors(Black),
                modifier = Modifier
                    .padding(top = 120.dp)
                    .widthIn(300.dp)
                    .heightIn(60.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 25.sp,
                    color = White
                )
            }
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}