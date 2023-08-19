package com.example.todojetpackcompose.presentation.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.todojetpackcompose.data.datastore.AuthenticationService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsView(
    listsViewModel: ListsViewModel,
    authenticationService: AuthenticationService
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My App")
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            authenticationService.onLogout()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Más")
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Text(text = "Lists")
        }
    }
}