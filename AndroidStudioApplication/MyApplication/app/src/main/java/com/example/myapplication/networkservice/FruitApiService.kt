package com.example.myapplication.networkservice

import com.example.myapplication.datamodels.AddFruitRequest
import com.example.myapplication.datamodels.AddFruitResponse
import com.example.myapplication.datamodels.CalculateCostRequest
import com.example.myapplication.datamodels.CalculateCostResponse
import com.example.myapplication.datamodels.DeleteFruitResponse
import com.example.myapplication.datamodels.GenerateReceiptRequest
import com.example.myapplication.datamodels.Receipt
import com.example.myapplication.datamodels.UpdateFruitRequest
import com.example.myapplication.datamodels.UpdateFruitResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

//import com.example.fruitservice.model.*

interface FruitApiService {
    @POST("api/fruits/add")
    suspend fun addFruit(@Body request: AddFruitRequest): Response<AddFruitResponse>

    @PUT("api/fruits/update")
    suspend fun updateFruit(@Body request: UpdateFruitRequest): Response<UpdateFruitResponse>

    @DELETE("api/fruits/delete")
    suspend fun deleteFruit(@Query("name") fruitName: String): Response<DeleteFruitResponse>

    @POST("api/fruits/calculate")
    suspend fun calculateCost(@Body request: CalculateCostRequest): Response<CalculateCostResponse>

    @POST("api/receipt/generate")
    suspend fun generateReceipt(@Body request: GenerateReceiptRequest): Response<Receipt>
}