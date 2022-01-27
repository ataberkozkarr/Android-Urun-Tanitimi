package com.works.saticiOL.productmodels


import com.google.gson.annotations.SerializedName

data class Campaign(
    @SerializedName("campaignType")
    val campaignType: String?,
    @SerializedName("campaignTypeId")
    val campaignTypeId: String?
)