package com.example.animeapp.ui.screens.logInAndSignUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.R
import com.example.animeapp.ui.theme.AnimeAppTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginAndSignUpPage(
    title : String,
    description : String,
    isSignUpPage : Boolean,
    onButtonText : String,
    authSwitchMessage : String,
    onBackPressed : () -> Unit,
    onAuthSwitchClick : () -> Unit,
    onLoginAndSignUpButtonClicked : () -> Unit,
    viewModel: LoginAndSignUpViewModel ,

    modifier: Modifier = Modifier
) {
    val emailFocusRequester = remember { FocusRequester() }
    val userNameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val loginAndSignUpUiState by viewModel.uiState.collectAsState()
    val loginUiState by viewModel.loginUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(loginUiState) {
        if (loginUiState.isSuccess) {
            onLoginAndSignUpButtonClicked()
        }
    }
    Scaffold (
        topBar = { AnimeTopAppBar(
            title = "",
            isBackButton = true,
            onBackButtonClicked = onBackPressed,
            backGroundColor = MaterialTheme.colorScheme.secondary
        ) }

    ){
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondary)
                .verticalScroll(rememberScrollState())
            ,
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 70.dp, topEnd = 70.dp))
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(96.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.alpha(0.8f)
                )
                Spacer(modifier = Modifier.height(61.dp))
                LoginAndSignUpTextField(
                    textFieldValue = loginAndSignUpUiState.email,
                    onTextFieldValueChange = { newValue ->
                        viewModel.updateEmailTextFieldValue(
                            newValue
                        )
                    },
                    placeHolderValue = stringResource(R.string.email),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    focusRequester = emailFocusRequester,
                    onImeAction = {
                        if (isSignUpPage) userNameFocusRequester.requestFocus()
                        else passwordFocusRequester.requestFocus()
                    },
                    isError = viewModel.uiState.collectAsState().value.emailError != null,
                    errorMessage = if (isSignUpPage) viewModel.uiState.collectAsState().value.emailError else null
                )
                if (isSignUpPage) {
                    Spacer(modifier = Modifier.height(15.dp))
                    LoginAndSignUpTextField(
                        textFieldValue = loginAndSignUpUiState.userName,
                        onTextFieldValueChange = { newValue ->
                            viewModel.updateUserNameTextFieldValue(
                                newValue
                            )
                        },
                        placeHolderValue = stringResource(R.string.userName),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        focusRequester = userNameFocusRequester,
                        onImeAction = { passwordFocusRequester.requestFocus() },
                        isError = viewModel.uiState.collectAsState().value.userNameError != null,
                        errorMessage = viewModel.uiState.collectAsState().value.userNameError

                    )
                }
                Spacer(modifier = Modifier.height(31.dp))
                LoginAndSignUpTextField(
                    textFieldValue = loginAndSignUpUiState.password,
                    onTextFieldValueChange = { newValue ->
                        viewModel.updatePasswordTextFieldValue(
                            newValue
                        )
                    },
                    placeHolderValue = stringResource(R.string.password),
                    isPassword = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    focusRequester = passwordFocusRequester,
                    onImeAction = {
                        if (isSignUpPage) confirmPasswordFocusRequester.requestFocus()
                        else {
                            coroutineScope.launch {
                                viewModel.login(
                                    email = viewModel.uiState.value.email,
                                    password = viewModel.uiState.value.password
                                )
                            }
                            if (viewModel.loginUiState.value.isSuccess) {
                                onLoginAndSignUpButtonClicked()
                            }
                        }
                    },
                    isError = viewModel.uiState.collectAsState().value.passwordError != null,
                    errorMessage = if (isSignUpPage) viewModel.uiState.collectAsState().value.passwordError else
                        viewModel.loginUiState.collectAsState().value.errorMessage
                )
                if (isSignUpPage) {
                    Spacer(modifier = Modifier.height(15.dp))
                    LoginAndSignUpTextField(
                        textFieldValue = loginAndSignUpUiState.confirmPassword,
                        onTextFieldValueChange = { newValue ->
                            viewModel.updateConfirmPasswordTextFieldValue(
                                newValue
                            )
                        },
                        placeHolderValue = stringResource(R.string.confirm_password),
                        isPassword = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        focusRequester = confirmPasswordFocusRequester,
                        onImeAction = {
                            coroutineScope.launch {
                                viewModel.saveAccount(signUpState = viewModel.uiState.value)
                                viewModel.login(
                                    email = viewModel.uiState.value.email,
                                    password = viewModel.uiState.value.password
                                )
                            }
                            if (viewModel.loginUiState.value.isSuccess) {
                                onLoginAndSignUpButtonClicked()
                            }
                        },
                        isError = viewModel.uiState.collectAsState().value.confirmPasswordError != null,
                        errorMessage = viewModel.uiState.collectAsState().value.confirmPasswordError
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            viewModel.updateRememberMeValue(!loginAndSignUpUiState.isRememberMeOn)
                        }
                    ) {
                        Icon(
                            imageVector = if (loginAndSignUpUiState.isRememberMeOn)
                                Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                            contentDescription = stringResource(R.string.remember_me_icon),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.remember_me),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (!isSignUpPage) {
                        Text(text = stringResource(R.string.forget_password))
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (isSignUpPage) {
                                viewModel.saveAccount(signUpState = viewModel.uiState.value)
                            }
                            viewModel.login(
                                email = viewModel.uiState.value.email,
                                password = viewModel.uiState.value.password
                            )
                        }
                        if (viewModel.loginUiState.value.isSuccess) {
                            onLoginAndSignUpButtonClicked()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .sizeIn(minHeight = 41.dp)
                ) {
                    Text(
                        text = onButtonText,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = authSwitchMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable { onAuthSwitchClick() }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.or_continue_with),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.alpha(0.8f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook_icons),
                        contentDescription = stringResource(R.string.facebook_icon),
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(80.dp)
                            .background(MaterialTheme.colorScheme.secondary)

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = stringResource(R.string.google_icon),
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }

        }
    }
}

@Composable
fun LoginAndSignUpTextField(
    textFieldValue : String,
    onTextFieldValueChange : (String) -> Unit,
    placeHolderValue : String,
    isPassword : Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester,
    onImeAction: () -> Unit,
    isError : Boolean= false,
    errorMessage : String? = ""
) {
    TextField(
        value = textFieldValue,
        onValueChange = {onTextFieldValueChange(it)},
        singleLine = true,
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),

        placeholder = {
            Text(
                text = placeHolderValue,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                color = Color(0xff777777)

            )
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onAny = { onImeAction() }),

    )
    errorMessage.let { error ->
        if (error != null) {
            Text(text = error, color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginAndSignupPagePreview() {
    AnimeAppTheme {

    }
}