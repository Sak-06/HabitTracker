package com.example.habittracker

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.google.android.play.integrity.internal.h
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)
                ) {
                    HomePage(LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun HomePage(context: Context) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround ,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp)
        ){
            Text(
                text = "Home",
                modifier = Modifier.padding(bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Month",
                modifier = Modifier.padding(bottom = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Icon(
                painter = painterResource(R.drawable.menu_icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp).background(color = Color(0xFFFFFFFF))
            )
        }
        Image(
            painter = painterResource(R.drawable.homepage),
            modifier = Modifier.size(width = 450.dp, height = 300.dp).padding(start = 0.dp,top=0.dp,end=0.dp,bottom=30.dp),
            contentDescription = null

        )
        val calendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        var selectedDate by remember { mutableStateOf("Date") } // Default text

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
            onValueChange = {
                selectedDate=it
            },
            readOnly = true, // Prevent manual input
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
        IconButton(onClick = { val intent = Intent(context,HabitActivity::class.java)
        context.startActivity(intent)}) {
            Icon(
                painter = painterResource(R.drawable.add_icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color(0xFF5CB5D0)
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom ,
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
            Icon(
                painter = painterResource(R.drawable.add_icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color(0xFF5CB5D0)
            )
        }

    }
}

@Preview(showBackground = true , backgroundColor = 0xFFFFFFFF)
@Composable
fun GreetingPreview() {
    HomePage(LocalContext.current)
}