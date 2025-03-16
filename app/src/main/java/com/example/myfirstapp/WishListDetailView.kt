package com.example.myfirstapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
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

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    //conditional states
    if (id != null) {
        val wish = viewModel.getWishById(id)
            .collectAsState(initial = Wish(title = "", description = "", id = null))
        viewModel.onWishTitleChange(
            TextFieldValue(
                wish.value.title,
                selection = TextRange(wish.value.title.length)
            )
        )
        viewModel.onWishDescriptionChange(
            TextFieldValue(
                wish.value.description,
                selection = TextRange(wish.value.description.length)
            )
        )
    } else {
        viewModel.onWishTitleChange(TextFieldValue(""))
        viewModel.onWishDescriptionChange(TextFieldValue(""))
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
                    .padding(8.dp)
                    .focusRequester(focusRequester1),
                label = { Text(text = "Title") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester2.requestFocus() }
                ),
            )

            OutlinedTextField(
                value = viewModel.wishDescriptionState,
                onValueChange = { value ->
                    viewModel.onWishDescriptionChange(value)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .focusRequester(focusRequester2),
                label = { Text(text = "Description") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
            )

            Button(
                onClick = {

                    if (viewModel.wishTitleState.text.isEmpty() || viewModel.wishDescriptionState.text.isEmpty()) {
                        snackMessage.value = "Enter fields"
                        scope.launch {
                            snackbarHostState.showSnackbar(snackMessage.value)
                        }
                    } else {
                        if (id == null) {
                            //create
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.text,
                                    description = viewModel.wishDescriptionState.text,
                                    id = null
                                )
                            )
                            snackMessage.value = "Wish has been created!"
                        } else {
                            //update
                            viewModel.updateWish(
                                Wish(
                                    title = viewModel.wishTitleState.text,
                                    description = viewModel.wishDescriptionState.text,
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

            LaunchedEffect(Unit) {
                focusRequester1.requestFocus()
            }
        }
    }
}