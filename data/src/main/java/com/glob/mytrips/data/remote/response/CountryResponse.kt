package com.glob.mytrips.data.remote.response

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("nameCountry")
    val name: String,
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("idCountry")
    val id: Int,
    @SerializedName("statesCountry")
    val states: List<StateResponse>
)