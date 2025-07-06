package com.example.myapplication.repository

//import com.example.myapplication.model.*
//import com.example.myapplication.network.NetworkModule
import com.example.myapplication.datamodels.AddFruitRequest
import com.example.myapplication.datamodels.AddFruitResponse
import com.example.myapplication.datamodels.CalculateCostRequest
import com.example.myapplication.datamodels.CalculateCostResponse
import com.example.myapplication.datamodels.DeleteFruitResponse
import com.example.myapplication.datamodels.GenerateReceiptRequest
import com.example.myapplication.datamodels.Receipt
import com.example.myapplication.datamodels.UpdateFruitRequest
import com.example.myapplication.datamodels.UpdateFruitResponse
import com.example.myapplication.networkservice.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FruitRepository {
    private val apiService = NetworkModule.fruitApiService

    suspend fun addFruit(fruitName: String, pricePerKg: Double): Result<AddFruitResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.addFruit(AddFruitRequest(fruitName, pricePerKg))
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to add fruit: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun updateFruit(fruitName: String, pricePerKg: Double): Result<UpdateFruitResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateFruit(UpdateFruitRequest(fruitName, pricePerKg))
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to update fruit: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun deleteFruit(fruitName: String): Result<DeleteFruitResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.deleteFruit(fruitName)
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to delete fruit: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun calculateCost(fruitName: String, quantity: Double): Result<CalculateCostResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.calculateCost(CalculateCostRequest(fruitName, quantity))
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to calculate cost: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun generateReceipt(
        fruitQuantities: Map<String, Double>,
        amountPaid: Double,
        cashier: String
    ): Result<Receipt> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.generateReceipt(
                GenerateReceiptRequest(fruitQuantities, amountPaid, cashier)
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to generate receipt: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
