package com.example.pokermao.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("number") val numero: String,
    @SerializedName("name") val nome: String,
    @SerializedName("imageURL") val urlImagem: String,
    @SerializedName("attack") var ataque: Int,
    @SerializedName("defense") var defesa: Int,
    @SerializedName("velocity") var velocidade: Int,
    @SerializedName("ps") var ps: Int
) : Parcelable