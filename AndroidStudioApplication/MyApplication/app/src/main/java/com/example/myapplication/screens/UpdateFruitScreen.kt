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
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.StatusMessages
import com.example.myapplication.viewmodel.FruitViewModel

@Composable
fun UpdateFruitScreen(
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
            text = "Update Fruit Price",
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
            label = { Text("New Price per KG (Ksh )") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val price = pricePerKg.toDoubleOrNull()
                if (fruitName.isNotBlank() && price != null && price > 0) {
                    viewModel.updateFruit(fruitName.trim(), price)
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
                )
            } else {
                Text("Update Fruit")
            }
        }

        StatusMessages(uiState)
    }
}