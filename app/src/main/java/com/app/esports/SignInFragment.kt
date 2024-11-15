package com.app.esports

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
import com.app.esports.databinding.FragmentSignInBinding

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
        if (rememberMe){
            val username: String = sharedPreferences.getString(SAVED_USERNAME, "").toString()
            val password: String = sharedPreferences.getString(SAVED_PASSWORD, "").toString()

            if (authenticate(username, password)){
                val intent = Intent(context, MainActivity::class.java)
                context?.startActivity(intent)
            }
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
                    if (authenticate(txtUsername.text.toString(), txtPassword.text.toString())) {
                        Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                        val sharedPreferences: SharedPreferences =
                            requireContext().getSharedPreferences(
                                "SETTING", Context.MODE_PRIVATE
                            )
                        val editor = sharedPreferences.edit()

                        if (cbRememberMe.isChecked) {
                            editor.putBoolean(REMEMBER_ME, true)
                            editor.putString(SAVED_USERNAME, txtUsername.text.toString())
                            editor.putString(SAVED_PASSWORD, txtPassword.text.toString())
                        } else {
                            editor.putBoolean(REMEMBER_ME, false)
                            editor.remove(SAVED_USERNAME)
                            editor.remove(SAVED_PASSWORD)
                        }
                        editor.apply()

                        val intent = Intent(context, MainActivity::class.java)
                        context?.startActivity(intent)

                        activity?.finish()
                    } else {
                        Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT)
                            .show()
                    }
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
    private fun authenticate(username: String, password: String): Boolean {
        for (user in MainActivity.userData) {
            if (user.username == username && user.password == password) {
                return true
            }
        }
        return false
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
