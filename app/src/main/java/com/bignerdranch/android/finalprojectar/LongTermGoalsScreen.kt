package com.bignerdranch.android.finalprojectar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
fun LongTermGoalsScreen(navController: NavHostController) {
    var goalTitle by remember { mutableStateOf("") }
    var completionDate by remember { mutableStateOf("") }
    val longTermGoals = remember { mutableStateListOf<LongTermGoal>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Long-Term Goals") },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create a Long-Term Goal", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

            TextField(
                value = goalTitle,
                onValueChange = { goalTitle = it },
                label = { Text("Goal Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = completionDate,
                onValueChange = { completionDate = it },
                label = { Text("Completion Date (e.g., 2024-12-31)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (goalTitle.isNotBlank() && completionDate.isNotBlank()) {
                        longTermGoals.add(LongTermGoal(goalTitle, completionDate))
                        goalTitle = ""
                        completionDate = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Goal")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Your Long-Term Goals", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            LazyColumn {
                items(longTermGoals.size) { index ->
                    val goal = longTermGoals[index]
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
                            Text("Goal: ${goal.title}")
                            Text("Completion Date: ${goal.completionDate}")
                        }
                    }
                }
            }
        }
    }
}

data class LongTermGoal(val title: String, val completionDate: String)
