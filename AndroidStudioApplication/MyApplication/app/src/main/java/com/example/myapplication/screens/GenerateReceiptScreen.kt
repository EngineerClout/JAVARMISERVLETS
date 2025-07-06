package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.composables.AddFruitItemDialog
import com.example.myapplication.composables.FruitItemRow
import com.example.myapplication.composables.ReceiptCard
import com.example.myapplication.composables.StatusMessages
import com.example.myapplication.uidataclasses.FruitItem
import com.example.myapplication.viewmodel.FruitViewModel

@Composable
fun GenerateReceiptScreen(
    viewModel: FruitViewModel,
    modifier: Modifier = Modifier
) {
    var cashier by remember { mutableStateOf("") }
    var amountPaid by remember { mutableStateOf("") }
    var fruitItems by remember { mutableStateOf(listOf<FruitItem>()) }
    var showAddItemDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Generate Receipt",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = cashier,
            onValueChange = { cashier = it },
            label = { Text("Cashier Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Fruit Items Section
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Fruit Items",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { showAddItemDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Item"
                        )
                    }
                }

                if (fruitItems.isEmpty()) {
                    Text(
                        text = "No items added yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    fruitItems.forEachIndexed { index, item ->
                        FruitItemRow(
                            item = item,
                            onRemove = {
                                fruitItems = fruitItems.toMutableList().apply {
                                    removeAt(index)
                                }
                            }
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = amountPaid,
            onValueChange = { amountPaid = it },
            label = { Text("Amount Paid (Ksh )") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val amount = amountPaid.toDoubleOrNull()
                if (cashier.isNotBlank() && amount != null && amount >= 0 && fruitItems.isNotEmpty()) {
                    val fruitQuantities = fruitItems.associate { it.name to it.quantity }
                    viewModel.generateReceipt(fruitQuantities, amount, cashier.trim())
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading && cashier.isNotBlank() &&
                    amountPaid.toDoubleOrNull()?.let { it >= 0 } == true &&
                    fruitItems.isNotEmpty()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Generate Receipt")
            }
        }

        // Display receipt
        uiState.generatedReceipt?.let { receipt ->
            ReceiptCard(receipt)
        }

        StatusMessages(uiState)

        // Add Item Dialog
        if (showAddItemDialog) {
            AddFruitItemDialog(
                onDismiss = { showAddItemDialog = false },
                onAdd = { item ->
                    fruitItems = fruitItems + item
                    showAddItemDialog = false
                }
            )
        }
    }
}