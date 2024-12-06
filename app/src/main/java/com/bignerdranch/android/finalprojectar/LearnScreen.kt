package com.bignerdranch.android.finalprojectar

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("learn_prefs", Context.MODE_PRIVATE) }
    var note by remember { mutableStateOf("") }

    // Load saved note
    LaunchedEffect(Unit) {
        note = sharedPreferences.getString("learn_note", "") ?: ""
    }

    // Save note when it changes
    LaunchedEffect(note) {
        sharedPreferences.edit().putString("learn_note", note).apply()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learn") },
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
            Text("Your Notes", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            TextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Add your learning notes here") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "This section is a work in progress. Stay tuned for more features!",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
