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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.animeapp.R
import com.example.animeapp.ui.theme.AnimeAppTheme

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
    viewModel: LoginAndSignUpViewModel ,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary),
    ){

            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),

            )
        Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 70.dp, topEnd = 70.dp))
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(20.dp)
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
                    textFieldValue = viewModel.emailTextFieldValue.collectAsState().value,
                    onTextFieldValueChange = { newValue -> viewModel.updateEmailTextFieldValue(newValue) } ,
                    placeHolderValue = stringResource(R.string.email_or_username)
                )
                Spacer(modifier = Modifier.height(31.dp))
               LoginAndSignUpTextField(
                   textFieldValue = viewModel.passwordTextFieldValue.collectAsState().value,
                   onTextFieldValueChange ={newValue -> viewModel.updatePasswordTextFieldValue(newValue)} ,
                   placeHolderValue = stringResource(R.string.password)
               )
                if(isSignUpPage) {
                    Spacer(modifier = Modifier.height(15.dp))
                  LoginAndSignUpTextField(
                      textFieldValue = viewModel.confirmPasswordTextFieldValue.collectAsState().value,
                      onTextFieldValueChange = {newValue -> viewModel.updateConfirmPasswordTextFieldValue(newValue)},
                      placeHolderValue = stringResource(R.string.confirm_password)
                  )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
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
                    if(!isSignUpPage) {
                        Text(text = stringResource(R.string.forget_password))
                    }
                }
                Spacer(modifier = Modifier.height(45.dp))
                Button(
                    onClick = { },
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
                Spacer(modifier = Modifier.height(19.dp))
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

@Composable
fun LoginAndSignUpTextField(
    textFieldValue : String,
    onTextFieldValueChange : (String) -> Unit,
    placeHolderValue : String
) {
    TextField(
        value = textFieldValue,
        onValueChange = {onTextFieldValueChange(it)},
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
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
        }
    )
}
@Preview(showBackground = true)
@Preview(showBackground = true)
@Composable
fun LoginAndSignupPagePreview() {
    AnimeAppTheme {

    }
}