package com.app.esports

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

class SignIn : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var cbRememberMe: CheckBox
    private lateinit var lblForgotPassword: TextView
    private lateinit var lblSignUp: TextView
    private lateinit var pbTogglePassword: ImageView


    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
        txtUsername = binding.txtUsername
        txtPassword = binding.txtPassword
        btnSignIn = binding.btnSignIn
        cbRememberMe = binding.cbRememberMe
        lblForgotPassword = binding.lblForgotPassword
        lblSignUp = binding.lblSignUp
        pbTogglePassword = binding.pbTogglePassword

        btnSignIn.setOnClickListener {
            if (validateInputs()) {
                if (checkUser(txtUsername.text.toString(), txtPassword.text.toString())) {
                    Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                    //navigate to home page
                } else {
                    Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lblForgotPassword.setOnClickListener {
            Toast.makeText(context, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        }

        lblSignUp.setOnClickListener {
            // Navigate to Sign Up screen
            Toast.makeText(context, "Sign Up clicked", Toast.LENGTH_SHORT).show()
        }

        pbTogglePassword.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun validateInputs(): Boolean {
        if (txtUsername.text.isEmpty()) {
            txtUsername.error = "Username is required"
            return false
        }
        if (txtPassword.text.isEmpty()) {
            txtPassword.error = "Password is required"
            return false
        }
        return true
    }

    private fun togglePasswordVisibility() {
        if (txtPassword.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            txtPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            pbTogglePassword.setImageResource(R.drawable.ic_visibility_off)
        } else {
            txtPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            pbTogglePassword.setImageResource(R.drawable.ic_visibility)
        }
        txtPassword.setSelection(txtPassword.text.length)
    }
    private fun checkUser(username: String, password: String): Boolean {
        val mainActivity = activity as MainActivity
        for (user in mainActivity.userData) {
            if (user.username == username && user.password == password) {
                return true
            }
        }
        return false
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignIn().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
    }
}
