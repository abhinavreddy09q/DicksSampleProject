package com.example.dicks.api

import com.example.dicks.data.SeatGeekResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SeatGeekService {
    @GET("events")
    fun getListOfSeatGeek(
        @Query("client_id") clientId: String = "MjE3OTY0NTR8MTYxOTUwOTA0Ni40Nzc5OTMy",
        @Query("q") searchQuery: String?
    ): Observable<SeatGeekResponse>
}