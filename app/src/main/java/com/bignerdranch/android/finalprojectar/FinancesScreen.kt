package com.bignerdranch.android.finalprojectar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen(navController: NavHostController) {
    // A list to store financial goals
    var goalName by remember { mutableStateOf("") }
    var goalAmount by remember { mutableStateOf("") }
    var savedAmount by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(false) }
    var reminderTime by remember { mutableStateOf("") }
    val goals = remember { mutableStateListOf<FinancialGoal>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finances") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Set Financial Goals", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

            // Input fields
            TextField(
                value = goalName,
                onValueChange = { goalName = it },
                label = { Text("Goal Name") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = goalAmount,
                onValueChange = { goalAmount = it },
                label = { Text("Goal Amount ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = savedAmount,
                onValueChange = { savedAmount = it },
                label = { Text("Saved Amount ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = reminderEnabled,
                    onCheckedChange = { reminderEnabled = it }
                )
                Text("Set Reminder")
            }

            if (reminderEnabled) {
                TextField(
                    value = reminderTime,
                    onValueChange = { reminderTime = it },
                    label = { Text("Reminder Time (e.g., 10:00 AM)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save goal button
            Button(
                onClick = {
                    if (goalName.isNotBlank() && goalAmount.isNotBlank() && savedAmount.isNotBlank()) {
                        goals.add(
                            FinancialGoal(
                                name = goalName,
                                totalAmount = goalAmount.toDoubleOrNull() ?: 0.0,
                                savedAmount = savedAmount.toDoubleOrNull() ?: 0.0,
                                reminder = if (reminderEnabled) reminderTime else null
                            )
                        )
                        // Clear fields
                        goalName = ""
                        goalAmount = ""
                        savedAmount = ""
                        reminderEnabled = false
                        reminderTime = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Goal")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display saved goals
            Text("Saved Goals", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            LazyColumn {
                items(goals.size) { index ->
                    val goal = goals[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text("Name: ${goal.name}")
                            Text("Goal Amount: $${goal.totalAmount}")
                            Text("Saved: $${goal.savedAmount}")
                            goal.reminder?.let {
                                Text("Reminder: $it")
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data class for financial goals
data class FinancialGoal(
    val name: String,
    val totalAmount: Double,
    val savedAmount: Double,
    val reminder: String?
)
