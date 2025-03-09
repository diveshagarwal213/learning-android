package com.example.myfirstapp

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//            containerColor = colorResource(id = R.color.app_bar_color),
//            titleContentColor = colorResource(id = R.color.white),
        ),
        navigationIcon = {
            if (!title.contains("WishList")) {
                IconButton(onClick = onBackNavClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
    )
}