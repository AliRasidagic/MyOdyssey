package com.example.travelapp.ui.pages

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.example.travelapp.ui.navigation.Navigation
import com.example.travelapp.R
import com.example.travelapp.data.database.LoginInfo
import com.example.travelapp.data.view_models.RegistrationLoginViewModel
import kotlinx.coroutines.launch

@Composable
fun Registration(
    viewModel: RegistrationLoginViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible1 by remember { mutableStateOf(false) }
    var passwordVisible2 by remember { mutableStateOf(false) }
    val uiState = viewModel.state

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val alphabeticRegex = Regex("[a-zA-Z_]+")
    val isNameValid = uiState.username?.let { alphabeticRegex.matches(it) } == true

    val registerButton: () -> Unit = {
        navController.navigate(Navigation.Login.name)
    }

    val registerButtonClick: () -> Unit = {
        if (uiState.username?.isEmpty() == true || uiState.email.isEmpty() || uiState.password.isEmpty() || uiState.confirmPassword.isEmpty()) {
            errorMessage = "Please fill in all fields"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()) {
            errorMessage = "Invalid email address"
        } else if (!isNameValid) {
            errorMessage = "Invalid name. Only letters and _ are allowed."
        } else {
            val loginInfo =
                uiState.username?.let {
                    LoginInfo(
                        username = it,
                        email = uiState.email,
                        password = uiState.password,
                        profilePicture = ""
                    )
                }
            viewModel.viewModelScope.launch {
                val existingUser = viewModel.checkUserByEmailRegister(uiState.email)
                if (existingUser) {
                    errorMessage = "User with the same email already exists"
                } else {
                    if (uiState.password != uiState.confirmPassword) {
                        errorMessage = "Passwords do not match"
                    } else {
                        if (uiState.password.length < 8) {
                            errorMessage = "Password too short"
                        } else {
                            if (loginInfo != null) {
                                viewModel.addUser(loginInfo)
                            }
                            navController.navigate(Navigation.Profile.name)
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
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
                value = uiState.username ?: "",
                onValueChange = { text ->
                    viewModel.onNameChange(text)
                },
                label = {
                    Text(
                        "Username",
                        color = Black
                    )
                },
                modifier = Modifier
                    .padding(top = 70.dp)
                    .widthIn(300.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black
                )
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
                    .padding(top = 16.dp)
                    .widthIn(300.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
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
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black
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
                        if (passwordVisible1) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(
                        onClick = { passwordVisible1 = !passwordVisible1 },
                        content = {
                            Icon(
                                imageVector = visibilityIcon,
                                contentDescription = null,
                                tint = Black
                            )
                        }
                    )
                },
                visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black
                )
            )
            TextField(
                value = uiState.confirmPassword,
                onValueChange = { text ->
                    viewModel.onConfirmChange(text)
                },
                label = {
                    Text(
                        "Confirm Password",
                        color = Black
                    )
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .widthIn(300.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                },
                trailingIcon = {
                    val visibilityIcon =
                        if (passwordVisible2) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(
                        onClick = { passwordVisible2 = !passwordVisible2 },
                        content = {
                            Icon(
                                imageVector = visibilityIcon,
                                contentDescription = null,
                                tint = Black
                            )
                        }
                    )
                },
                visualTransformation = if (passwordVisible2) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    registerButtonClick()
                    keyboardController?.hide()
                }),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedTextColor = Black,
                    unfocusedTextColor = Black
                )
            )
            OutlinedButton(
                onClick = registerButton,
                colors = ButtonDefaults.buttonColors(Black),
                modifier = Modifier
                    .padding(top = 100.dp)
                    .widthIn(300.dp)
                    .heightIn(60.dp)
            ) {
                Text(
                    text = "Register",
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