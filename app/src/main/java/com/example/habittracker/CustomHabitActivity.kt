package com.example.habittracker

import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.ListFormatter.Width
import android.os.Bundle
import android.text.format.Time
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.HabitTrackerTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomHabitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)
                ){
                    CustomHabit(LocalContext.current)
                }
            }
        }
    }
}
@Composable
fun IconGrid(onIconSelected: (ImageVector) -> Unit, onDismiss: ()-> Unit){
    val icons = listOf(Icons.Default.Call, Icons.Default.AccountBox , Icons.Default.Build , Icons.Default.Person)
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        items(icons.size){index ->
            Icon(
                imageVector = icons[index],
                contentDescription = "Icon",
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp)
                   .clickable { onIconSelected(icons[index]) }
            )
        }
    }
}
@Composable
fun ColorGrid(onColorSelected: (Color) -> Unit, onDismiss: ()-> Unit){
    val colors = listOf(Color(0xFFFFFFFF),Color(0xFFFFFFFF),Color(0xFFFFFFFF),Color(0xFFFFFFFF),Color(0xFFFFFFFF))
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        items(colors.size){index ->
            Box(modifier = Modifier.background( color= colors[index], shape = RectangleShape)
                .size(60.dp)
                .padding(8.dp)
                .clickable { onColorSelected(colors[index]) }
            )
        }
    }
}

@Composable
fun CustomHabit(context: Context) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var name by remember { mutableStateOf("") }
        var selectedIcon by remember { mutableStateOf(Icons.Default.Face) }
        var selectedColor by remember { mutableStateOf(Color.Gray) }
        var showIconPicker by remember { mutableStateOf(false) }
        var showColorPicker by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = { Text("Name") },
            modifier = Modifier.size(width = 400.dp, height = 75.dp)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)

        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(modifier = Modifier
                .size(width = 180.dp, height = 150.dp)
                .clickable { showIconPicker = true } // Toggle expansion
                .padding(16.dp)
                .border(1.dp, color = Color(0xFFB4B4B4), shape = RectangleShape)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = selectedIcon,
                        contentDescription = "Selected Icon",
                        modifier = Modifier.size(88.dp).padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "Select an icon",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold

                    )
                }


            }
            if (showIconPicker) {
                IconGrid(
                    onIconSelected = { selectedIcon = it },
                    onDismiss = { showIconPicker = false }
                )
            }

            Box(modifier = Modifier
                .size(width = 180.dp, height = 150.dp)
                .clickable { showColorPicker = true } // Toggle expansion
                .padding(16.dp)
                .border(1.dp, color = Color.LightGray, shape = RectangleShape)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.size(88.dp).padding(10.dp)
                            .background(color = selectedColor)
                    )
                    Text(
                        text = " Select a Color",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
//                modifier = Modifier.fillMaxSize()
//                    .padding(horizontal = 20.dp, vertical = 15.dp),
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Medium
                    )
                }
            }
            if (showColorPicker) {
                ColorGrid(
                    onColorSelected = { selectedColor = it },
                    onDismiss = { showColorPicker = false }
                )
            }

        }
        var develop by remember { mutableStateOf(false) }
        var quit by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(width = 180.dp, height = 150.dp)
                    .border(1.dp, Color.White, RectangleShape)
                    .clickable { develop = true } // Toggle expansion
                    .padding(16.dp)
                    .background(color = Color(0xFFB2FF6A))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.develop),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "Develop",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.Green,
                    )

                }
            }
            Box(
                modifier = Modifier.size(width = 180.dp, height = 150.dp)
                    .border(1.dp, Color.LightGray, RectangleShape)
                    .clickable { quit = true } // Toggle expansion
                    .padding(16.dp)
                    .background(color = Color(0xFF57B0FF))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.quit),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "Quit",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.Blue,
                    )

                }
            }
        }
        val calendar = Calendar.getInstance()
        val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        var selectedTime by remember { mutableStateOf("Time") }
        val timePicker = TimePickerDialog(
            context, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedTime = timeFormatter.format(calendar.time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(top = 30.dp)
        ) {
            Text(text="Set Reminder",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        OutlinedTextField(
            value = selectedTime,
            onValueChange = {
                selectedTime = it
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
            trailingIcon = {
                IconButton(onClick = { timePicker.show() }) {
                    Icon(
                        painter = painterResource(R.drawable.time),
                        contentDescription = "Time picker",
                        tint = Color.Black

                    )
                }
            }
        )
    }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun GreetingPreview2() {
    CustomHabit(LocalContext.current)
}