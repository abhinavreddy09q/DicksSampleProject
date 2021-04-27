package com.example.dicks.repo

import com.example.dicks.api.SeatGeekService
import javax.inject.Inject

class SeatGeekRepository @Inject constructor(private val service: SeatGeekService) {
    fun getListOfSeatGeeks(searchQuery: String?) =
        service.getListOfSeatGeek(searchQuery = searchQuery)
}