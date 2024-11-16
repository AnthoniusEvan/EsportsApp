package com.app.esports

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.esports.databinding.FragmentSignUpBinding
import org.json.JSONObject

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            cbTerms.setOnCheckedChangeListener{
                _, status ->
                if (status) btnRegister.isEnabled = true;
                else btnRegister.isEnabled = false;
            }

            btnRegister.setOnClickListener{
                if (validateInputs()){
                    RegisterAccount(etFirstName.text.toString(),etLastName.text.toString(),etUsername.text.toString(),etPassword.text.toString())
                }
            }
            ivBack.setOnClickListener{
                activity?.onBackPressedDispatcher?.onBackPressed()
            }

            etUsername.setOnFocusChangeListener{
                _, status -> if (!status){
                    isUsernameUnique(etUsername.text.toString())
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        with(binding){
            if (etFirstName.text.isEmpty()){
                etFirstName.error = "First name is required"
                return false
            }
            if (etUsername.text.isEmpty()){
                etUsername.error = "Username is required"
                return false
            }

            if (etPassword.text.isEmpty()){
                etPassword.error = "Password is required"
                return false
            }

            if (etPassword.text.toString() != etRepeatPassword.text.toString()){
                etRepeatPassword.error = "Repeat password does not match with password"
                return false
            }

            if (!isUsernameUnique){
                etUsername.error = "Username already exists"
                return false
            }
        }
        return true
    }
    var isUsernameUnique:Boolean = true
    private fun isUsernameUnique(username: String){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.xyz/native/160922001/api/checkExistingUser.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "TRUE"){
                    binding.etUsername.error = "Username already exists"
                    isUsernameUnique=false
                }
                else isUsernameUnique=true
            },
            {
                Log.e("apiresult", it.message.toString())
            },
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = mutableMapOf<String, String>()
                params["username"] = username
                return params
            }
        }
        q.add(stringRequest)
    }

    private fun RegisterAccount(first_name: String,last_name: String,username: String,password: String){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.xyz/native/160922001/api/register.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    Toast.makeText(activity, "Succesfully registered account!", Toast.LENGTH_LONG).show()
                    val newId = obj.getInt("id")
                    val newUser = User(newId, first_name, last_name, username, password)
                    val intent = Intent(context, MainActivity::class.java)

                    intent.putExtra(MainActivity.USER, newUser)
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
                params["first_name"] = first_name
                params["last_name"] = last_name
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        q.add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}