package com.example.habittracker

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    HomePage(LocalContext.current, null)
                }
            }
        }
    }
    @Composable
    fun SignOutButton(googleAuth: GoogleAuth?){
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = {
            coroutineScope.launch {
                googleAuth?.signOut()
                FirebaseAuth.getInstance().signOut()
                FirebaseAuth.getInstance().currentUser?.reload()
                val prefs = context.getSharedPreferences("habit_prefs", Context.MODE_PRIVATE)
                prefs.edit().clear().apply()
                Toast.makeText(context,"Signed Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SignupActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)

            }
        }) {
            Text("Sign Out")
        }
    }

    @Composable
    fun HomePage(context: Context, oneTapClient: SignInClient?){
        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName ?: "User Name"
        val userPhotoUrl = user?.photoUrl?.toString()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val googleAuth = remember { oneTapClient?.let { GoogleAuth(context, it) } }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if(userPhotoUrl != null){
                            AsyncImage(
                                model=userPhotoUrl,
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        }
                        else {
                            Image(
                                painter = painterResource(R.drawable.female),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(userName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(25.dp))
                        ClickableText(text = AnnotatedString("Settings"), onClick = { /* Navigate to Settings */ })
                        Spacer(modifier = Modifier.height(25.dp))
                        ClickableText(text = AnnotatedString("About Us"), onClick = { /* Navigate to About Us */ })
                        Spacer(modifier = Modifier.height(25.dp))
                        ClickableText(text = AnnotatedString("Feedback"), onClick = { /* Navigate to Feedback */ })
                        Spacer(modifier = Modifier.height(40.dp))
                        SignOutButton(googleAuth)
                    }

                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                    Text("Home", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Month", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.menu_icon),
                            contentDescription = "Menu",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                    }
                }

                Image(
                    painter = painterResource(R.drawable.homepage),
                    modifier = Modifier.size(width = 450.dp, height = 300.dp)
                        .padding(bottom = 30.dp),
                    contentDescription = null
                )
                var showdialogue by remember { mutableStateOf(false) }
                val calendar = Calendar.getInstance()
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                var selectedDate by remember { mutableStateOf("Date") }

                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val pickedDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }
                        selectedDate = dateFormatter.format(pickedDate.time)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
                    trailingIcon = {
                        IconButton(onClick = { datePicker.show() }) {
                            Icon(
                                painter = painterResource(R.drawable.calendar_icon),
                                contentDescription = "Calendar",
                                tint = Color.Black
                            )
                        }
                    }
                )

                Text(
                    text = "Add an Activity/Habit to get started",
                    modifier = Modifier.padding(bottom = 40.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                IconButton( onClick = {showdialogue =!showdialogue}) {
                    Icon(
                        painter = painterResource(R.drawable.add_icon),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color(0xFF5CB5D0)
                    )
                    if (showdialogue) {
                        AlertDialog(
                            onDismissRequest = { showdialogue = false },
                            confirmButton = {
                                Text(
                                    text = "Habit",
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(context, HabitActivity::class.java)
                                            context.startActivity(intent)
                                        }
                                        .padding(10.dp),
                                    fontSize = 16.sp,
                                    color = Color.Blue
                                )
                            },
                            dismissButton = {
                                Text(
                                    text = "Task",
                                    modifier = Modifier
                                        .clickable {
                                            val intent = Intent(context, HabitActivity::class.java)
                                            context.startActivity(intent)
                                        }
                                        .padding(10.dp),
                                    fontSize = 16.sp,
                                    color = Color.Blue
                                )
                            },
                            shape = AlertDialogDefaults.shape
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxSize().padding(bottom = 25.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.home),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.tasks),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.habit),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.progress),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    IconButton(onClick = {
                        val intent = Intent(context, HabitActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.add_icon),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF5CB5D0)
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
    @Composable
    fun HomeScreenPreview() {
        val oneTapClient: SignInClient?=null
        HomePage(LocalContext.current,oneTapClient!! )
    }
}
