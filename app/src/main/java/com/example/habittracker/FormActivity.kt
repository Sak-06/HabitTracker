package com.example.habittracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.HabitTrackerTheme



class FormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(
                    modifier =  Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)
                ) {
                    Formreg(LocalContext.current)
                }
            }
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun Formreg(context: Context){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.reg),
            modifier = Modifier.size(width = 450.dp, height = 300.dp).padding(start = 0.dp,top=0.dp,end=0.dp,bottom=70.dp),
            contentDescription = null
        )
        var name by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf("") }
        var selectedCounty by remember { mutableStateOf("Select Country") }
        var expanded by remember { mutableStateOf(false) }
        val countries = listOf("USA", "Canada", "India", "UK", "Australia", "Germany", "France","Indonesia")
        val isValid =name.isNotBlank() && age.isNotBlank() && gender.isNotBlank() && selectedCounty.isNotBlank()
        OutlinedTextField(
            value = name,
            onValueChange ={
                name=it
            },
            placeholder = { Text("Name") },
            modifier = Modifier.size(width = 400.dp, height = 75.dp).padding(start = 10.dp,end=10.dp, top=10.dp, bottom = 10.dp),

        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = age,
                onValueChange = { newvalue ->
                    if (newvalue.all { it.isDigit() }) {
                        age = newvalue
                    }

                },
                placeholder = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.size(width = 200.dp, height = 73.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),

                )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { gender = "Male" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.male),
                    contentDescription = "Male",
                    modifier = Modifier.size(48.dp),

                )
                Text(
                    text = "Male",
                    color = if (gender == "Male") Color.Blue else Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp)) // Space between icons

            // Female Selection
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { gender = "Female" }
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.female),
                    contentDescription = "Female",

                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "Female",
                    color = if (gender == "Female") Color.Blue else Color.Gray,
                    fontSize = 14.sp
                )
            }

        }
        Box(
            modifier = Modifier.size(height = 95.dp, width = 460.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(bottom = 40.dp)
                .border(1.dp,Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                ,
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = selectedCounty, color = Color.Black,
                    modifier = Modifier.padding(start = 10.dp))

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(text = country) },
                        onClick = {
                            selectedCounty = country
                            expanded = false

                        }
                    )
                }
            }
        }
       Button(
           onClick = {val intent = Intent(context, MainActivity::class.java)
               context.startActivity(intent)},
           enabled = isValid,
           modifier = Modifier.size(width = 140.dp, height = 60.dp).fillMaxWidth().padding(bottom = 15.dp),
           colors = ButtonDefaults.buttonColors(
               contentColor = Color.Black,
               disabledContentColor = Color.Black,
               disabledContainerColor = Color(0xFF92E4F3),
           ),
           shape = RectangleShape
       ) {
           Modifier.padding(bottom = 15.dp)
           Row(horizontalArrangement = Arrangement.SpaceBetween ,
               verticalAlignment = Alignment.CenterVertically){
               Text("Next",Modifier.padding(horizontal = 10.dp), fontSize = 20.sp)
               Icon( painter = painterResource(R.drawable.next_icon), contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Black)
           }
       }
        Button(
            onClick = {val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)},
            enabled = true,
            modifier = Modifier.size(width = 140.dp, height = 60.dp).fillMaxWidth().padding(bottom = 15.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.LightGray
            ),
            shape = RectangleShape
        ) {
            Modifier.padding(bottom = 15.dp)
            Text("Skip",Modifier.padding(horizontal = 10.dp), fontSize = 20.sp)

        }



    }
}

@Preview(showBackground = true , backgroundColor = 0xFFFFFF)
@Composable
fun FormPreview() {
    Formreg(LocalContext.current)
}