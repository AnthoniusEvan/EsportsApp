package com.app.esports

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.SignInFragment.Companion.REMEMBER_ME
import com.app.esports.SignInFragment.Companion.SAVED_PASSWORD
import com.app.esports.SignInFragment.Companion.SAVED_USERNAME
import com.app.esports.ui.auth.AuthActivity
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        val rememberMe : Boolean = sharedPreferences.getBoolean(REMEMBER_ME, false)

        if (rememberMe){
            val username: String = sharedPreferences.getString(SAVED_USERNAME, "").toString()
            val password: String = sharedPreferences.getString(SAVED_PASSWORD, "").toString()

            authenticate(username, password)
        }
        else{
            Thread {
                try {
                    Thread.sleep(2000)
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }
    private fun authenticate(username: String, password: String){
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.xyz/native/160922001/api/login.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")
//                    val sType = object : TypeToken<User>() { }.type
//                    val active_user:User = Gson().fromJson(data.toString(), sType) as User

                    val userObj = data.getJSONObject(0)
                    val active_user: User = User(userObj.getInt("id"), userObj.getString("first_name"), userObj.getString("last_name"), userObj.getString("username"), userObj.getString("password"), userObj.getString("role"))

                    Log.d("apiresult", active_user.toString())

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(MainActivity.USER, active_user)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this, obj.getString("message"), Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = mutableMapOf<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        q.add(stringRequest)
    }
}