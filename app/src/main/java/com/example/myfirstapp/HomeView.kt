package com.example.myfirstapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfirstapp.data.DummyWish
import com.example.myfirstapp.data.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    Scaffold(
        topBar = { AppBarView(title = "WishList") },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(ScreenAddEditDetailView(id = null))
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        val wishList = viewModel.getAllWishes.collectAsState(initial = emptyList())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(wishList.value, key = { wish -> wish.id!! }) { wish ->

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart || it == SwipeToDismissBoxValue.StartToEnd) {
                            viewModel.deleteWish(wish)
                        }
                        true
                    }

                )
                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    enableDismissFromEndToStart = true,
                    backgroundContent = {
                        val color by
                        animateColorAsState(
                            when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.Settled -> Color.LightGray
                                SwipeToDismissBoxValue.StartToEnd -> Color.Red
                                SwipeToDismissBoxValue.EndToStart -> Color.Red
                            }
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd,

                            ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),

                    ) {
                    WishListItem(wish, onClick = {
                        navController.navigate(ScreenAddEditDetailView(id = wish.id))
                    })
                }
            }
        }
    }
}

// Wish list Item
@Composable
fun WishListItem(
    wish: Wish,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(wish.title, fontWeight = FontWeight.ExtraBold)
            Text(wish.description)
        }
    }
}
