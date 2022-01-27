package com.works.saticiOL.productmodels


import com.google.gson.annotations.SerializedName

data class Img(
    @SerializedName("normal")
    val normal: String?,
    @SerializedName("thumb")
    val thumb: String?
)