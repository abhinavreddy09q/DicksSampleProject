package com.example.dicks.data

data class SeatGeekResponse(val events: List<Events>)


data class Events(
    val id: String = "",
    val title: String = "",
    val datetime_utc: String = "",
    val venue: Venue,
    val performers: List<Performers>
)

data class Venue(
    val display_location: String
)

data class Performers(val image: String)
