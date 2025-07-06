package com.example.myapplication.datamodels

data class FruitPrice(
    val fruitName: String,
    val pricePerKg: Double
)

data class AddFruitRequest(
    val fruitName: String,
    val pricePerKg: Double
)

data class AddFruitResponse(
    val success: Boolean,
    val message: String
)

data class UpdateFruitRequest(
    val fruitName: String,
    val pricePerKg: Double
)

data class UpdateFruitResponse(
    val success: Boolean,
    val message: String
)

data class DeleteFruitResponse(
    val success: Boolean,
    val message: String
)

data class CalculateCostRequest(
    val fruitName: String,
    val quantity: Double
)

data class CalculateCostResponse(
    val success: Boolean,
    val fruitName: String? = null,
    val quantity: Double? = null,
    val pricePerKg: Double? = null,
    val totalCost: Double? = null,
    val message: String? = null
)

data class GenerateReceiptRequest(
    val fruitQuantities: Map<String, Double>,
    val amountPaid: Double,
    val cashier: String
)

data class Receipt(
    val items: List<String>,
    val totalCost: Double,
    val amountPaid: Double,
    val change: Double,
    val cashier: String,
    val timestamp: String
)

data class ErrorResponse(
    val error: String
)