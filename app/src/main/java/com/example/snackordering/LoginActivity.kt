package com.example.snackordering

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.snackordering.ui.theme.SnackOrderingTheme
import com.example.snackordering.ui.theme.primary
import com.example.snackordering.ui.theme.transparent
import com.example.snackordering.ui.theme.white

class LoginActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent {
            SnackOrderingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(this, databaseHelper)
                }
            }
        }
    }
}


@Composable
fun LoginScreen(context: Context, databaseHelper: UserDatabaseHelper) {

    Image(painterResource(id = R.drawable.login), contentDescription = "",
//        alpha =0.6F,
        contentScale = ContentScale.FillHeight,

    )

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
            color = Color.Black,
            text = "Login"
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username",color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 14.dp), shape = RoundedCornerShape(16.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF5500),
                backgroundColor = Color.Transparent)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password",color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 14.dp), shape = RoundedCornerShape(16.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = primary,
                backgroundColor = transparent)
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
Spacer(modifier =  Modifier.height(8.dp))
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    val user = databaseHelper.getUserByUsername(username)
                    if (user != null && user.password == password) {
                        error = "Successfully log in"
                        context.startActivity(
                            Intent(
                                context,
                                MainPage::class.java
                            )
                        )
                        //onLoginSuccess()
                    }
                        if (user != null && user.password == "admin") {
                            error = "Successfully log in"
                            context.startActivity(
                                Intent(
                                    context,
                                    AdminActivity::class.java
                                )
                            )
                        }
                        else {
                            error =  "Invalid username or password"
                        }

                } else {
                    error = "Please fill all fields"
                }
            },
            Modifier.size(width = 200.dp, height = 46.dp),shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = primary)
        ) {
            Text(text = "Login")
        }
        Row() {
            Text(
                modifier = Modifier.padding(top = 14.dp), text = "Don't Have an account ?"
            )
            TextButton(onClick = {
                context.startActivity(
                    Intent(
                        context,
                        MainActivity::class.java
                    )
                )
            }
            )
            {
                Spacer(modifier = Modifier.width(4.dp))
                Text(color = primary, text = "Sign up", textDecoration = TextDecoration.Underline) }
        }
            TextButton(onClick = {
            })

            {
                Text(color = white,text = "Forget password?")
            }

    }
}
private fun startMainPage(context: Context) {
    val intent = Intent(context, MainPage::class.java)
    ContextCompat.startActivity(context, intent, null)
}