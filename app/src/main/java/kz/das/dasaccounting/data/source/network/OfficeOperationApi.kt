package kz.das.dasaccounting.data.source.network

import kz.das.dasaccounting.core.extensions.ApiResponseMessage
import kz.das.dasaccounting.data.entities.common.InventoryGetRequest
import kz.das.dasaccounting.data.entities.common.InventorySendRequest
import kz.das.dasaccounting.data.entities.office.OfficeInventoryEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OfficeOperationApi {

    @GET("/api/materials/get")
    suspend fun getMaterials(): Response<List<OfficeInventoryEntity>>

    @POST("/api/goods/get/tmc")
    suspend fun acceptInventory(@Body inventoryRequest: InventoryGetRequest): Response<ApiResponseMessage>

    @POST("/api/goods/send/tmc")
    suspend fun sendInventory(@Body inventoryRequest: InventorySendRequest): Response<ApiResponseMessage>

}