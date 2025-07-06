package com.example.myapplication.viewmodel

//import com.example.fruitservice.model.*
//import com.example.fruitservice.repository.FruitRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.datamodels.CalculateCostResponse
import com.example.myapplication.datamodels.Receipt
import com.example.myapplication.repository.FruitRepository
import kotlinx.coroutines.launch

class FruitViewModel : ViewModel() {

    private val repository = FruitRepository()

    var uiState by mutableStateOf(FruitUiState())
        private set

    fun addFruit(fruitName: String, pricePerKg: Double) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            repository.addFruit(fruitName, pricePerKg)
                .onSuccess { response ->
                    uiState = uiState.copy(
                        isLoading = false,
                        successMessage = response.message,
                        lastOperationSuccess = response.success
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error",
                        lastOperationSuccess = false
                    )
                }
        }
    }

    fun updateFruit(fruitName: String, pricePerKg: Double) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            repository.updateFruit(fruitName, pricePerKg)
                .onSuccess { response ->
                    uiState = uiState.copy(
                        isLoading = false,
                        successMessage = response.message,
                        lastOperationSuccess = response.success
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error",
                        lastOperationSuccess = false
                    )
                }
        }
    }

    fun deleteFruit(fruitName: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            repository.deleteFruit(fruitName)
                .onSuccess { response ->
                    uiState = uiState.copy(
                        isLoading = false,
                        successMessage = response.message,
                        lastOperationSuccess = response.success
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error",
                        lastOperationSuccess = false
                    )
                }
        }
    }

    fun calculateCost(fruitName: String, quantity: Double) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            repository.calculateCost(fruitName, quantity)
                .onSuccess { response ->
                    if (response.success && response.totalCost != null) {
                        uiState = uiState.copy(
                            isLoading = false,
                            calculatedCost = response,
                            lastOperationSuccess = true
                        )
                    } else {
                        uiState = uiState.copy(
                            isLoading = false,
                            errorMessage = response.message ?: "Calculation failed",
                            lastOperationSuccess = false
                        )
                    }
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error",
                        lastOperationSuccess = false
                    )
                }
        }
    }

    fun generateReceipt(fruitQuantities: Map<String, Double>, amountPaid: Double, cashier: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            repository.generateReceipt(fruitQuantities, amountPaid, cashier)
                .onSuccess { receipt ->
                    uiState = uiState.copy(
                        isLoading = false,
                        generatedReceipt = receipt,
                        lastOperationSuccess = true
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error",
                        lastOperationSuccess = false
                    )
                }
        }
    }

    fun clearMessages() {
        uiState = uiState.copy(
            successMessage = null,
            errorMessage = null,
            calculatedCost = null,
            generatedReceipt = null
        )
    }
}

data class FruitUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val lastOperationSuccess: Boolean = false,
    val calculatedCost: CalculateCostResponse? = null,
    val generatedReceipt: Receipt? = null
)