package com.bignerdranch.android.finalprojectar

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("journal_prefs", Context.MODE_PRIVATE) }
    val gson = Gson()

    // State variables
    var currentEntry by remember { mutableStateOf("") }
    val journalEntries = remember { mutableStateListOf<JournalEntry>() }
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    // Load saved journal entries
    LaunchedEffect(Unit) {
        val savedEntriesJson = sharedPreferences.getString("journal_entries", null)
        if (savedEntriesJson != null) {
            val savedEntriesType = object : TypeToken<List<JournalEntry>>() {}.type
            val savedEntries: List<JournalEntry> = gson.fromJson(savedEntriesJson, savedEntriesType)
            journalEntries.addAll(savedEntries)
        }
    }

    // Save journal entries
    LaunchedEffect(journalEntries) {
        val toSave = gson.toJson(journalEntries)
        sharedPreferences.edit().putString("journal_entries", toSave).apply()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journal") },
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
            Text("Today's Entry: $today", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            TextField(
                value = currentEntry,
                onValueChange = { currentEntry = it },
                label = { Text("Write your entry here") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (currentEntry.isNotBlank()) {
                        journalEntries.removeAll { it.date == today }
                        journalEntries.add(JournalEntry(today, currentEntry))
                        currentEntry = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Entry")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Previous Entries", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            LazyColumn {
                items(journalEntries.size) { index ->
                    val entry = journalEntries[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Date: ${entry.date}", fontSize = 16.sp)
                            Text("Entry: ${entry.text}", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

data class JournalEntry(val date: String, val text: String)
