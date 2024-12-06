package com.bignerdranch.android.finalprojectar

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(navController: NavHostController) {
    // Obtain the context and SharedPreferences
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE) }
    val gson = Gson()

    // State variables
    var title by remember { mutableStateOf("") }
    var task by remember { mutableStateOf("") }
    val toDoLists = remember { mutableStateListOf<ToDoList>() }
    var selectedList by remember { mutableStateOf<ToDoList?>(null) }

    // Load saved to-do lists when the screen is first displayed
    LaunchedEffect(Unit) {
        val savedListsJson = sharedPreferences.getString("todo_lists", null)
        if (savedListsJson != null) {
            val savedListsType = object : TypeToken<List<ToDoList>>() {}.type
            val savedLists: List<ToDoList> = gson.fromJson(savedListsJson, savedListsType)
            toDoLists.addAll(savedLists)
        }
    }

    // Save the to-do lists whenever they change
    LaunchedEffect(toDoLists) {
        val toSave = gson.toJson(toDoLists)
        sharedPreferences.edit().putString("todo_lists", toSave).apply()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-Do Lists") },
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
            if (selectedList == null) {
                // Main view: Create a new list or select an existing list
                Text("Create a New To-Do List", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("List Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            toDoLists.add(ToDoList(title, mutableListOf()))
                            title = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create List")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text("Your To-Do Lists", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                LazyColumn {
                    items(toDoLists.size) { index ->
                        val toDoList = toDoLists[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedList = toDoList },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Title: ${toDoList.title}", fontSize = 18.sp)
                                Text("Tasks: ${toDoList.tasks.size} tasks")
                            }
                        }
                    }
                }
            } else {
                // Task management view
                Text("Add Tasks to '${selectedList?.title}'", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

                TextField(
                    value = task,
                    onValueChange = { task = it },
                    label = { Text("Task Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (task.isNotBlank()) {
                            selectedList?.tasks?.add(task)
                            task = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Task")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text("Tasks", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                LazyColumn {
                    selectedList?.tasks?.let { tasks ->
                        items(tasks.size) { index ->
                            Text("- ${tasks[index]}", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { selectedList = null },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Lists")
                }
            }
        }
    }
}

data class ToDoList(val title: String, val tasks: MutableList<String>)
