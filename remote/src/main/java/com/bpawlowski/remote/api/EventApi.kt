package com.bpawlowski.remote.api

import com.bpawlowski.remote.model.EventDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventApi {
    @GET("events/")
    suspend fun getEvents(): Response<List<EventDto>>

    @POST("events/")
    suspend fun postEvent(@Body body: EventDto): Response<EventDto>

    @PUT("events/{id}")
    suspend fun putEvent(@Path("id") id: Long, @Body body: EventDto): Response<Unit>

    @DELETE("events/{id}")
    suspend fun deleteEvent(@Path("id") id: Long): Response<Unit>
}
