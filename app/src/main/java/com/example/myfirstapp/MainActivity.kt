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
fun UnitDropdownMenu(
    label: String,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val units = listOf("Centimeters", "Meters", "Feet", "Millimeters")
    Box {
        Button(onClick = onExpand) {
            Text(label)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
        }
        DropdownMenu(expanded = isExpanded, onDismissRequest = onDismiss) {
            units.forEach { unit ->
                DropdownMenuItem(text = { Text(unit) }, onClick = {
                    onDismiss()
                    onSelect(unit)
                })
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    // States
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var isInputDropdownExpanded by remember { mutableStateOf(false) }
    var isOutputDropdownExpanded by remember { mutableStateOf(false) }
    val conversionFactors = mapOf(
        "Centimeters" to 0.01,
        "Meters" to 1.0,
        "Feet" to 0.3048,
        "Millimeters" to 0.001
    )

    // handlers
    fun convertUnits() {
        val inputFactor = conversionFactors[inputUnit] ?: 1.0
        val outputFactor = conversionFactors[outputUnit] ?: 1.0
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * inputFactor / outputFactor * 100.0).roundToInt() / 100.0
        outputValue = result.toString()
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
            label = { Text("Enter value") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row {
            UnitDropdownMenu(
                label = inputUnit,
                isExpanded = isInputDropdownExpanded,
                onExpand = { isInputDropdownExpanded = true },
                onDismiss = { isInputDropdownExpanded = false },
                onSelect = {
                    inputUnit = it
                    convertUnits()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            UnitDropdownMenu(
                label = outputUnit,
                isExpanded = isOutputDropdownExpanded,
                onExpand = { isOutputDropdownExpanded = true },
                onDismiss = { isOutputDropdownExpanded = false },
                onSelect = {
                    outputUnit = it
                    convertUnits()
                }
            )
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