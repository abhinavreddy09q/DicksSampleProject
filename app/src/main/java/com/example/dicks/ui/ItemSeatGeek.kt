package com.example.dicks.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemSeatGeek(
    val imageUrl: String,
    val title: String,
    val location: String,
    val date: String
) : Parcelable