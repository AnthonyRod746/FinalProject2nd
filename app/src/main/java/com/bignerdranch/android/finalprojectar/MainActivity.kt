@file:OptIn(ExperimentalMaterial3Api::class)

package com.bignerdranch.android.finalprojectar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bignerdranch.android.finalprojectar.ui.theme.FinalProjectARTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectARTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("finances") { FinancesScreen(navController) }
        composable("music") { MusicScreen(navController) }
        composable("to_do_list") { ToDoListScreen(navController) }
        composable("long_term") { LongTermGoalsScreen(navController) }
        composable("journal") { JournalScreen(navController) } // Updated for JournalScreen
        composable("learn") { LearnScreen(navController) } // Updated for LearnScreen
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("It is a great day today isn't it?")
                },
                actions = {
                    IconButton(onClick = { /* Open settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0x72EF3DDD)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                "Welcome to Your Assistant!",
                fontSize = 38.sp,
                color = Color.Black,
                letterSpacing = 2.sp, // Add letter spacing
                textAlign = TextAlign.Center // Ensure proper alignment
            ) // Adjusted App Title
            Spacer(modifier = Modifier.height(16.dp))
            Text("😊 Select how I can help below", fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            ButtonWithColor("Finances", Color(0xFFFFA500)) { navController.navigate("finances") }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonWithColor("Music", Color(0xFF00FFFF)) { navController.navigate("music") }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonWithColor("To-Do Lists", Color(0xFF00FF00)) { navController.navigate("to_do_list") }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonWithColor("Long-Term Goals", Color(0x953838B4)) { navController.navigate("long_term") }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonWithColor("Journal", Color(0xFFFF00FF)) { navController.navigate("journal") }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonWithColor("Learn", Color(0xFFFF0000)) { navController.navigate("learn") }
        }
    }
}


@Composable
fun ButtonWithColor(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.Black)
    }
}
