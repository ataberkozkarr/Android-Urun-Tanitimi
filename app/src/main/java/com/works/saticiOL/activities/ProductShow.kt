package com.works.saticiOL.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.works.saticiOL.databinding.ActivityProductShowBinding
import com.works.saticiOL.productmodels.Bilgiler
import com.works.saticiOL.productmodels.ModelProduct
import com.works.saticiOL.api.Service
import com.works.saticiOL.api.Clients
import com.works.saticiOL.api.UserObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductShow : AppCompatActivity() {

    private lateinit var bind : ActivityProductShowBinding

    lateinit var sha : SharedPreferences
    lateinit var edit : SharedPreferences.Editor
    private var arrPro: List<Bilgiler> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductShowBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.txtName.text = "Hoşgeldiniz, "+ UserObj.name +" "+ UserObj.surname

        val service = Clients.getClient().create(Service::class.java)
        val dataService = service.getProduct()

        dataService.enqueue(object : Callback<ModelProduct> {
            override fun onResponse(call: Call<ModelProduct>, response: Response<ModelProduct>) {
                if(response.isSuccessful){
                    val pro = response.body()
                    if(pro!=null){
                        arrPro = pro.products?.get(0)!!.bilgiler!!
                        UserObj.arrPros = arrPro
                        val adapter  = ProductAdapt(this@ProductShow,arrPro)
                        bind.listView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<ModelProduct>, t: Throwable) {
                Toast.makeText(this@ProductShow, "İnternet bağlantınızı kontrol ediniz.", Toast.LENGTH_SHORT).show()
            }
        })

        bind.listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@ProductShow, ProductDetail::class.java)
            intent.putExtra("index",i)
            startActivity(intent)

        }


    }

    override fun onBackPressed() {
        val i = Intent(this@ProductShow, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}