package com.app.esports

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.FragmentSignInBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

private const val ARG_USER = "user"

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        val rememberMe : Boolean = sharedPreferences.getBoolean(REMEMBER_ME, false)

        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(ARG_USER)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignIn.setOnClickListener {
                if (validateInputs()) {
                    authenticate(txtUsername.text.toString(), txtPassword.text.toString())
                }
            }

            lblForgotPassword.setOnClickListener {
                Toast.makeText(context, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
            }

            lblSignUp.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.main, SignUpFragment())
                    addToBackStack(null)
                    commit()
                }
            }

            pbTogglePassword.setOnClickListener {
                togglePasswordVisibility()
            }
        }
    }

    private fun validateInputs(): Boolean {
        with(binding){
            if (txtUsername.text.isEmpty()) {
                txtUsername.error = "Username is required"
                return false
            }
            if (txtPassword.text.isEmpty()) {
                txtPassword.error = "Password is required"
                return false
            }
        }
        return true
    }

    private fun togglePasswordVisibility() {
        with(binding){
            if (txtPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                txtPassword.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                pbTogglePassword.setImageResource(R.drawable.ic_visibility_off)
            } else {
                txtPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                pbTogglePassword.setImageResource(R.drawable.ic_visibility)
            }
            txtPassword.setSelection(txtPassword.text.length)
        }
    }
    private fun authenticate(username: String, password: String){
        val q = Volley.newRequestQueue(activity)
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
                    val active_user: User = User(userObj.getInt("id"), userObj.getString("first_name"), userObj.getString("last_name"), userObj.getString("username"), userObj.getString("password"))

                    Log.d("apiresult", active_user.toString())

                    val sharedPreferences: SharedPreferences =
                        requireContext().getSharedPreferences(
                            "SETTING", Context.MODE_PRIVATE
                        )
                    val editor = sharedPreferences.edit()

                    if (binding.cbRememberMe.isChecked) {
                        editor.putBoolean(REMEMBER_ME, true)
                        editor.putString(SAVED_USERNAME, binding.txtUsername.text.toString())
                        editor.putString(SAVED_PASSWORD, binding.txtPassword.text.toString())
                    } else {
                        editor.putBoolean(REMEMBER_ME, false)
                        editor.remove(SAVED_USERNAME)
                        editor.remove(SAVED_PASSWORD)
                    }
                    editor.apply()


                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra(MainActivity.USER, active_user)
                    context?.startActivity(intent)
                    activity?.finish()
                }
                else {
                    Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_LONG).show()
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
    companion object {
        val REMEMBER_ME = "remember_me"
        val SAVED_USERNAME = "saved_username"
        val SAVED_PASSWORD = "saved_password"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
    }
}
