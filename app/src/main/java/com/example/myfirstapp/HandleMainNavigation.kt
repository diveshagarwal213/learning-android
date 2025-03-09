package com.example.myfirstapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object ScreenHomeView

@Serializable
data class ScreenAddEditDetailView(
    val id: Long?,
)

@Composable
fun HandleMainNavigation(viewModel: WishViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenHomeView) {
        composable<ScreenHomeView> {
            HomeView(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable<ScreenAddEditDetailView> {
            val args = it.toRoute<ScreenAddEditDetailView>()
            AddEditDetailView(id = args.id, viewModel = viewModel, navController = navController)
        }
    }
}

