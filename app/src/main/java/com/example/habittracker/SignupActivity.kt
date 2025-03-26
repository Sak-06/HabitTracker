package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.node.CanFocusChecker.start
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.habittracker.ui.theme.HabitTrackerTheme



class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)
                ){
                    Signup(LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun Signup(context: Context) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        var isButtonClicked by remember { mutableStateOf(false) }
        var isGoogleClicked by remember { mutableStateOf(false) }
        Image(
            painter = painterResource(R.drawable.signin),
            modifier = Modifier.size(width = 400.dp, height = 200.dp).padding(bottom = 10.dp),
            contentDescription = null,
        )
        Text(
            text ="Habit Tracker App",
            modifier = Modifier.padding(bottom = 40.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            text ="Create an Account",
            modifier = Modifier.padding(bottom = 20.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text ="Enter your email to sign up for this app",
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 14.sp
        )
        fun validateEmail(email: String):Boolean{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        OutlinedTextField(
            value = email,
            onValueChange = {
                email=it
                isError=!validateEmail(it)
                isButtonClicked=validateEmail(email)
            },
            placeholder = { Text("email@domain.com") },
            modifier = Modifier.size(width = 400.dp, height = 50.dp).padding(start = 10.dp,end=10.dp)

        )
        Button(
            onClick = { val intent = Intent(context, FormActivity::class.java)
                context.startActivity(intent)
                Toast.makeText(context,"Successfully Signed in",Toast.LENGTH_SHORT).show()},
            enabled = isButtonClicked,
            modifier = Modifier.size(width = 120.dp, height = 65.dp).fillMaxWidth().padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.Black,
            ),
            shape = RectangleShape
            )
        {
            Text("Continue")
        }
        Text(
            text = "-------------- or ---------------",
            modifier = Modifier.padding(top = 20.dp,bottom=20.dp)

        )
        Button(
            onClick = {
                Toast.makeText(context,"Google Signed in",Toast.LENGTH_SHORT).show()},
            enabled = isGoogleClicked,
            modifier = Modifier.size(width = 300.dp, height = 90.dp).fillMaxWidth().padding(bottom = 35.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.LightGray,
            ),
            shape = RectangleShape
        )
        {Modifier.padding(bottom = 15.dp)
            Row(horizontalArrangement = Arrangement.Center ,
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(R.drawable.img), contentDescription = "google",Modifier.padding(horizontal = 10.dp).size(25.dp))
            Text("Continue with Google",Modifier.padding(horizontal = 10.dp))
        }
        }
        Text(
            text =" By clicking continue, you agree to our Terms of Service and Privacy Policy",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )


    }
}

@Preview(showBackground = true , backgroundColor= 0xFFFFFFFF)
@Composable
fun SignupPreview() {
       Signup(LocalContext.current)
}