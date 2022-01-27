package com.works.saticiOL.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.works.saticiOL.databinding.ActivityRegisterBinding
import com.works.saticiOL.models.UserRegister
import com.works.saticiOL.api.Service
import com.works.saticiOL.api.Clients
import com.works.saticiOL.api.UserObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var bind : ActivityRegisterBinding

    lateinit var sha : SharedPreferences
    lateinit var edit : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnRegister.setOnClickListener {

            val name:String = bind.txtName.text.toString().trim()
            val surname:String = bind.txtSurname.text.toString().trim()
            val phone:String = bind.txtPhone.text.toString().trim()
            val email:String = bind.txtEmail.text.toString().trim()
            val password:String = bind.txtPassword.text.toString().trim()

            fun isEmailValid(email:CharSequence):Boolean{
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "isim boş bırakılamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(surname)){
                Toast.makeText(this, "soyisim boş bırakılamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this, "Telefon boş bırakılamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Email boş bırakılamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!isEmailValid(email)){
                Toast.makeText(this, "Geçersiz mail adresi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Şifre boş bırakılamaz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val service = Clients.getClient().create(Service::class.java)
            val dataService = service.userRegister(name,surname,phone,email,password)

            dataService.enqueue(object : Callback<UserRegister> {
                override fun onResponse(call: Call<UserRegister>, response: Response<UserRegister>) {
                    if(response.isSuccessful){
                        val u = response.body()
                        if(u!=null){
                            val status = u.user?.get(0)?.durum
                            val message =  u.user?.get(0)?.mesaj
                            if(status == true){
                                sha = getSharedPreferences("user", MODE_PRIVATE)
                                edit = sha.edit()

                               // edit.putString("email",email)
                                edit.putString("userID",u.user?.get(0)?.kullaniciId)
                                edit.putString("name",name)
                                edit.putString("surname",surname)
                                edit.commit()

                                UserObj.userID = u.user?.get(0)?.kullaniciId
                                UserObj.name = name
                                UserObj.surname = surname

                                Toast.makeText(this@Register, ""+message, Toast.LENGTH_SHORT).show()
                                val i = Intent(this@Register, ProductShow::class.java)
                                startActivity(i)
                                finish()
                            }
                            if(status == false){
                                Toast.makeText(this@Register, ""+message, Toast.LENGTH_SHORT).show()

                            }
                        }

                    }

                }
                override fun onFailure(call: Call<UserRegister>, t: Throwable) {
                    Toast.makeText(this@Register, "Register failed. Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }

            })


        }


    }


}