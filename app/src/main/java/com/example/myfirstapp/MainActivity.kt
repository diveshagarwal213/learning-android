package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyFirstAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RecipeApp(navController, modifier = Modifier.padding(innerPadding))
//                    RecipeScreen(modifier = Modifier.padding(innerPadding))
//                    Myapp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FirstScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Text("Fist Screen")
        OutlinedTextField(value = name, onValueChange = { name = it })
        Button(onClick = {
            navController.navigate("secondscreen/testname")
        }) {
            Text("Go to Next Screen")
        }
    }
}

@Composable
fun SecondScreen(modifier: Modifier = Modifier, name: String) {
    Column(modifier = modifier) {
        Text("Second Screen $name")
    }
}

@Composable
fun Myapp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "firstscreen") {
        composable("firstscreen") {
            FirstScreen(navController = navController, modifier = modifier)
        }
        composable("secondscreen/{name}") {
            val name = it.arguments?.getString("name") ?: "no Name"
            SecondScreen(modifier = modifier, name = name)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun UnitConverterPreview() {
//    MyFirstAppTheme {
////        CounterApp()
//    }
//}