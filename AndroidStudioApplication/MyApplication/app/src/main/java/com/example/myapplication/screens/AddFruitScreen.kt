package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.StatusMessages
import com.example.myapplication.viewmodel.FruitUiState
import com.example.myapplication.viewmodel.FruitViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddFruitScreen(
    viewModel: FruitViewModel,
    modifier: Modifier = Modifier
) {
    var fruitName by remember { mutableStateOf("") }
    var pricePerKg by remember { mutableStateOf("") }
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add New Fruit",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = fruitName,
            onValueChange = { fruitName = it },
            label = { Text("Fruit Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = pricePerKg,
            onValueChange = { pricePerKg = it },
            label = { Text("Price per KG (Ksh)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val price = pricePerKg.toDoubleOrNull()
                if (fruitName.isNotBlank() && price != null && price > 0) {
                    viewModel.addFruit(fruitName.trim(), price)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading && fruitName.isNotBlank() &&
                    pricePerKg.toDoubleOrNull()?.let { it > 0 } == true
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                    // modifier = Modifier.size().size(16.dp),

                )
            } else {
                Text("Add Fruit")
            }
        }

        // Status Messages
        StatusMessages(uiState)
    }
}
