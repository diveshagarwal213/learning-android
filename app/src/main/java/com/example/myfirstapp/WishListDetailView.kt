package com.example.myfirstapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfirstapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long?,
    viewModel: WishViewModel,
    navController: NavController
) {
    //states
    val snackMessage = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    //conditional states
    if (id == null) {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    } else {
        val wish = viewModel.getWishById(id)
            .collectAsState(initial = Wish(title = "", description = "", id = null))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }

    //UI
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppBarView(
                title = if (id == null) "Add Wish" else "Update Wish",
                onBackNavClicked = { navController.navigateUp() })
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            OutlinedTextField(
                value = viewModel.wishTitleState,
                onValueChange = { value ->
                    viewModel.onWishTitleChange(value)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text(text = "Title") }
            )

            OutlinedTextField(
                value = viewModel.wishDescriptionState,
                onValueChange = { value ->
                    viewModel.onWishDescriptionChange(value)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text(text = "Description") }
            )

            Button(
                onClick = {
                    if (viewModel.wishTitleState.isEmpty() || viewModel.wishDescriptionState.isEmpty()) {
                        snackMessage.value = "Enter fields"
                        scope.launch {
                            snackbarHostState.showSnackbar(snackMessage.value)
                        }
                    } else {
                        if (id == null) {
                            //create
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState,
                                    description = viewModel.wishDescriptionState,
                                    id = null
                                )
                            )
                            snackMessage.value = "Wish has been created!"
                        } else {
                            //update
                            viewModel.updateWish(
                                Wish(
                                    title = viewModel.wishTitleState,
                                    description = viewModel.wishDescriptionState,
                                    id = id
                                )
                            )
                            snackMessage.value = "Wish has been updated!"
                        }
                        scope.launch {
                            snackbarHostState.showSnackbar(snackMessage.value)
                            navController.navigateUp()
                        }
                    }
                }
            ) {
                Text(text = if (id == null) "Add Wish" else "Update Wish")
            }
        }
    }
}