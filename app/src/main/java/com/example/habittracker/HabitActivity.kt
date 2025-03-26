package com.example.habittracker

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.HabitTrackerTheme

class HabitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFFFF)
                ){
                    HabitSelection(LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun HabitSelection(context: Context) {
    var expandedIndex by remember { mutableStateOf(-1) } // Tracks which dropdown is open

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = "Create a new Habit", fontSize = 25.sp)
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close activity",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Habit Selection List
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top=80.dp).fillMaxWidth().animateContentSize()
        ) {
            HabitDropdown("Productivity Habit", listOf("Coding", "Full Stack", "App Development", "DSA"), Color(0xFFFD528A), 0, expandedIndex) { expandedIndex = it }
            HabitDropdown("Growth Habit", listOf("Reading", "Networking", "Skill Learning"), Color(0xFFB7FF9C), 1, expandedIndex) { expandedIndex = it }
            HabitDropdown("Health Habit", listOf("Exercise", "Yoga", "Meditation"), Color(0xFFD591FF), 2, expandedIndex) { expandedIndex = it }
            HabitDropdown("Social Habit", listOf("Gratitude", "Journaling", "Mindfulness"), Color(0xFFA2FFF0), 3, expandedIndex) { expandedIndex = it }

            // Custom Habit Button
            Box(
                modifier = Modifier.wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                    .padding(bottom = 40.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .clickable { val intent = Intent(context, CustomHabitActivity::class.java)
                        context.startActivity(intent) }
                    .animateContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Custom Habit",
                    modifier = Modifier.fillMaxWidth().background(color = Color(0xFFFFE76F))
                        .padding(horizontal = 20.dp, vertical = 15.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun HabitDropdown(title: String, items: List<String>, color: Color, index: Int, expandedIndex: Int, onExpand: (Int) -> Unit) {
    val isExpanded = index == expandedIndex
    var selectedItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 36.dp) // Space between items
            .animateContentSize() // Smooth animation for expanding
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .clickable { onExpand(if (isExpanded) -1 else index) } // Toggle expansion
                .padding(16.dp)
        ) {
            Text(
                text = if (selectedItem.isEmpty()) title else selectedItem,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEDEDED))
                    .padding(8.dp)
            ) {
                items.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedItem = item
                                onExpand(-1) // Collapse after selection
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HabitActivityPreview() {
    HabitSelection(LocalContext.current)
}