package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    //states
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableDoubleStateOf(1.00) }
    val oConversionFactor = remember { mutableDoubleStateOf(1.00) }

    // handlers
    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        outputValue =
            (((inputValueDouble * conversionFactor.doubleValue * 100.0 / oConversionFactor.doubleValue)).roundToInt() / 100.0).toString()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter  value") },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row {
            Box {
                Button(onClick = {
                    iExpanded = true
                }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, "Arrow Down")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = {
                    iExpanded = false
                }) {
                    DropdownMenuItem(text = {
                        Text("Centimeters")
                    }, onClick = {
                        iExpanded = false
                        inputUnit = "Centimeters"
                        conversionFactor.doubleValue = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = {
                        Text("Meters")
                    }, onClick = {
                        iExpanded = false
                        inputUnit = "Meters"
                        conversionFactor.doubleValue = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = {
                        Text("Feet")
                    }, onClick = {
                        iExpanded = false
                        inputUnit = "Feet"
                        conversionFactor.doubleValue = 0.3048
                        convertUnits()
                    })
                    DropdownMenuItem(text = {
                        Text("Millimeters")
                    }, onClick = {
                        iExpanded = false
                        inputUnit = "Millimeters"
                        conversionFactor.doubleValue = 0.001
                        convertUnits()
                    })
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = {
                    oExpanded = true
                }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = {
                    oExpanded = false
                }) {
                    DropdownMenuItem(text = {
                        Text("Centimeters")
                    }, onClick = {
                        oExpanded = false
                        outputUnit = "Centimeters"
                        oConversionFactor.doubleValue = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = {
                        Text("Meters")
                    }, onClick = {
                        oExpanded = false
                        outputUnit = "Meters"
                        oConversionFactor.doubleValue = 1.0
                        convertUnits()
                    })
                    DropdownMenuItem(text = {
                        Text("Feet")
                    }, onClick = {
                        oExpanded = false
                        outputUnit = "Feet"
                        oConversionFactor.doubleValue = 0.3048
                        convertUnits()
                    })
                    DropdownMenuItem(text = {
                        Text("Millimeters")
                    }, onClick = {
                        oExpanded = false
                        outputUnit = "Millimeters"
                        oConversionFactor.doubleValue = 0.001
                        convertUnits()
                    })
                }
            }

        }
        Text(
            text = "Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    MyFirstAppTheme {
        UnitConverter()
    }
}