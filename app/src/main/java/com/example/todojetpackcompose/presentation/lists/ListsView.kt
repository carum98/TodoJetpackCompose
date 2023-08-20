package com.example.todojetpackcompose.presentation.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todojetpackcompose.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsView(
    listsViewModel: ListsViewModel = hiltViewModel(),
    onAddList: () -> Unit,
    onOpenList: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = listsViewModel.state.value

    // Get lists
    LaunchedEffect(null) {
        println("Get lists")
        listsViewModel.onEvent(ListEvent.GetLists)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("ToDo App")
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            listsViewModel.logout()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Más")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddList()
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add list")
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.lists) { list ->
                    ListItem(
                        headlineText = {
                            Text(text = list.name)
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_circle_24),
                                contentDescription = "List icon",
                                tint = list.color
                            )
                        },
                        modifier = Modifier
                            .clickable {
                                onOpenList(list.id)
                            }
                    )
                }
            }
            if (state.error.isNotBlank()) {
                Text(text = state.error)
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}