package com.bignerdranch.android.finalprojectar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Music") },
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
            Text("Link Your Music Account", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

            if (!submitted) {
                // Input fields for email and password
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                )
/* watch this */
                Spacer(modifier = Modifier.height(16.dp))

                // Submit button
                Button(
                    onClick = { submitted = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit")
                }
            } else {
                // Display message after submission
                Text(
                    "Compatibility with these apps to come soon!",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = { submitted = false },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Go Back")
                }
            }
        }
    }
}
