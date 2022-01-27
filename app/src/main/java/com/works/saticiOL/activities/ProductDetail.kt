package com.works.saticiOL.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.works.saticiOL.databinding.ActivityProductDetailBinding
import android.text.method.ScrollingMovementMethod
import com.works.saticiOL.models.OrderModel
import com.works.saticiOL.api.Service
import com.works.saticiOL.api.Clients
import com.works.saticiOL.api.UserObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetail : AppCompatActivity() {

    private lateinit var bind:ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val index = intent.getIntExtra("index",0)

        val url = UserObj.arrPros.get(index).imgs!!.get(0).normal
        Glide.with(this@ProductDetail).load(url).into(bind.imgDetail)
        bind.txtTitle.text = UserObj.arrPros.get(index).productName
        bind.txtPrice.text = UserObj.arrPros.get(index).price
        bind.txtBrief.text = UserObj.arrPros.get(index).brief
        bind.txtDesc.text = UserObj.arrPros.get(index).description

        bind.txtBrief.setMovementMethod(ScrollingMovementMethod())
        bind.txtDesc.setMovementMethod(ScrollingMovementMethod())

        bind.btnOrder.setOnClickListener {

            val service = Clients.getClient().create(Service::class.java)
            val dataService = service.sendOrder(
                UserObj.userID!!, UserObj.arrPros.get(index).productId!!,
                UserObj.arrPros.get(index).productId!!)

            dataService.enqueue(object : Callback<OrderModel> {
                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                    if(response.isSuccessful){
                        val pro = response.body()
                        val status = pro?.order?.get(0)?.durum
                        val message = pro?.order?.get(0)?.mesaj
                        if(pro!=null){
                            if(status == true){
                                Toast.makeText(this@ProductDetail, ""+message, Toast.LENGTH_SHORT).show()
                            }
                            if(status == false){
                                Toast.makeText(this@ProductDetail, ""+message, Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                }
                override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                    Toast.makeText(this@ProductDetail, "Sipariş geçersiz.", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }
}