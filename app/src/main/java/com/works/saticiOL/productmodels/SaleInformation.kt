package com.works.saticiOL.productmodels


import com.google.gson.annotations.SerializedName

data class SaleInformation(
    @SerializedName("saleType")
    val saleType: String?,
    @SerializedName("saleTypeId")
    val saleTypeId: String?
)