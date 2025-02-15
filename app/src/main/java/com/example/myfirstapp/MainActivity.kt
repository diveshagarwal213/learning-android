package com.example.myfirstapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
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
//            val navController = rememberNavController()
            val viewModel: LocationViewModel = viewModel()
            MyFirstAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Myapp(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
//                    RecipeApp(navController, modifier = Modifier.padding(innerPadding))
//                    RecipeScreen(modifier = Modifier.padding(innerPadding))
//                    Myapp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//location my app
@Composable
fun Myapp(modifier: Modifier = Modifier, viewModel: LocationViewModel) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    LocationDisplay(modifier, locationUtils, context, viewModel)

}

@Composable
fun LocationDisplay(
    modifier: Modifier = Modifier,
    locationUtils: LocationUtils,
    context: Context,
    viewModel: LocationViewModel
) {
    val location = viewModel.location.value
    val address = location?.let {
        locationUtils.reverseGeoLocation(it)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                //Has Permissions
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (rationaleRequired) {
                    Toast.makeText(
                        context,
                        "Loaction Permission is required for this feature",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Toast.makeText(
                        context,
                        "Loaction Permission is required. please enable it in settings ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    )

    Column(modifier = modifier) {
        if (location != null) {
            Text("longitude:${location.longitude}, latitude:${location.latitude}, \n Address:$address")
        } else {
            Text("Location not available")

        }
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                //permission already granted
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text("Go to Next Screen")
        }
    }
}


//@Composable
//fun FirstScreen(modifier: Modifier = Modifier, navController: NavHostController) {
//    var name by remember { mutableStateOf("") }
//    Column(modifier = modifier) {
//        Text("Fist Screen")
//        OutlinedTextField(value = name, onValueChange = { name = it })
//        Button(onClick = {
//            navController.navigate("secondscreen/testname")
//        }) {
//            Text("Go to Next Screen")
//        }
//    }
//}
//
//@Composable
//fun SecondScreen(modifier: Modifier = Modifier, name: String) {
//    Column(modifier = modifier) {
//        Text("Second Screen $name")
//    }
//}
//
//@Composable
//fun Myapp(modifier: Modifier = Modifier) {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "firstscreen") {
//        composable("firstscreen") {
//            FirstScreen(navController = navController, modifier = modifier)
//        }
//        composable("secondscreen/{name}") {
//            val name = it.arguments?.getString("name") ?: "no Name"
//            SecondScreen(modifier = modifier, name = name)
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun UnitConverterPreview() {
//    MyFirstAppTheme {
////        CounterApp()
//    }
//}