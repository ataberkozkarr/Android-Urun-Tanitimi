package com.works.saticiOL.api

import com.works.saticiOL.models.OrderModel
import com.works.saticiOL.models.UserLogin
import com.works.saticiOL.models.UserRegister
import com.works.saticiOL.productmodels.ModelProduct
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("userLogin.php")
    fun userLogin (
        @Query("userEmail") userEmail: String,
        @Query("userPass") userPass: String,
        @Query("ref") ref: String = Clients.ref,
        @Query("face") face: String = "no",
        ) : Call<UserLogin>

    @GET("userRegister.php")
    fun userRegister (
        @Query("userName") userName: String,
        @Query("userSurname") userSurname: String,
        @Query("userPhone") userPhone: String,
        @Query("userMail") userMail: String,
        @Query("userPass") userPass: String,
        @Query("ref") ref: String = Clients.ref,
        ) : Call<UserRegister>

    @GET("product.php")
    fun getProduct (
        @Query("start") userName: String = "0",
        @Query("ref") ref: String = Clients.ref
        ) : Call<ModelProduct>

    @GET("orderForm.php")
    fun sendOrder (
        @Query("customerId") customerId: String,
        @Query("productId") productId: String,
        @Query("html") html:String,
        @Query("ref") ref: String = Clients.ref
    ) : Call<OrderModel>

}