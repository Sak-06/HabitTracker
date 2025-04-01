package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class SignupActivity : ComponentActivity() {
    private val googleAuthClient by lazy {
        GoogleAuth(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)
                ) {
                    val navController = rememberNavController()
                    val viewModel: SignInViewModel = viewModel()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignResult(signInResult)
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = state.isSignInSuccess) {
                        if (state.isSignInSuccess) {
                            Toast.makeText(applicationContext, "Sign-In Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignupActivity, FormActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            Signup(
                                context = LocalContext.current,
                                onSignClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable("FormActivity") {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Signup(context: Context, onSignClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        var isButtonClicked by remember { mutableStateOf(false) }

        Image(
            painter = painterResource(R.drawable.signin),
            modifier = Modifier
                .size(width = 400.dp, height = 200.dp)
                .padding(bottom = 10.dp),
            contentDescription = null,
        )

        Text(
            text = "Habit Tracker App",
            modifier = Modifier.padding(bottom = 40.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            text = "Create an Account",
            modifier = Modifier.padding(bottom = 20.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Enter your email to sign up for this app",
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 14.sp
        )

        fun validateEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = !validateEmail(it)
                isButtonClicked = validateEmail(email)
            },
            placeholder = { Text("email@domain.com") },
            modifier = Modifier
                .size(width = 400.dp, height = 50.dp)
                .padding(start = 10.dp, end = 10.dp)
        )

        Button(
            onClick = { val intent = Intent(context, FormActivity::class.java)
                      context.startActivity(intent)},
            enabled = isButtonClicked,
            modifier = Modifier
                .size(width = 120.dp, height = 65.dp)
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.Black,
            ),
            shape = RectangleShape
        ) {
            Text("Continue")
        }

        Text(
            text = "-------------- or ---------------",
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        )

        // Google Sign-In Button
        Button(
            onClick = onSignClick,
            modifier = Modifier
                .size(width = 300.dp, height = 90.dp)
                .fillMaxWidth()
                .padding(bottom = 35.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.LightGray,
            ),
            shape = RectangleShape
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = "google",
                    modifier = Modifier.padding(horizontal = 10.dp).size(25.dp)
                )
                Text("Continue with Google", Modifier.padding(horizontal = 10.dp))
            }
        }

        Text(
            text = "By clicking continue, you agree to our Terms of Service and Privacy Policy",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SignupPreview() {
    Signup(LocalContext.current, onSignClick = {})
}
