package com.dorukaneskiceri.currencyconvertkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun login(view: View){
        try{
            val url = "http://yusufozgul.com:8282/login"
            val jsonObject = JSONObject()

            jsonObject.put("username",usernameText.text)
            jsonObject.put("password",passwordText.text)
            val queue = Volley.newRequestQueue(this@LoginActivity)
            val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                Response.Listener {
                        response ->
                    if(response["success"].toString() == "true"){
                        Toast.makeText(this@LoginActivity,"Giriş başarılı.",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,FeaturesActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                },Response.ErrorListener {
                    Toast.makeText(this@LoginActivity,"Kullanıcı adı veya şifre hatalı.",Toast.LENGTH_LONG).show()
                })
            queue.add(request)

        }catch(e: Exception){
            e.printStackTrace()
        }
    }
}
