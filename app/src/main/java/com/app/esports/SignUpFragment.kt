package com.app.esports

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import com.app.esports.databinding.FragmentSignInBinding
import com.app.esports.databinding.FragmentSignUpBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
                    Toast.makeText(context, "Sign-up Successful!", Toast.LENGTH_SHORT).show()
                    RegisterAccount()

                    // without login
                    val intent = Intent(context, MainActivity::class.java)
                    context?.startActivity(intent)

                    activity?.finish()
                }
            }
            ivBack.setOnClickListener{
                activity?.onBackPressedDispatcher?.onBackPressed()
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
            if (!isUsernameUnique(etUsername.text.toString())){
                etUsername.error = "Username already exists"
                return false
            }

            if (etPassword.text.isEmpty()){
                etPassword.error = "Password is required"
                return false
            }

            if (etPassword.text != etRepeatPassword.text){
                etRepeatPassword.error = "Repeat password does not match with password"
                return false
            }
        }
        return true
    }

    private fun isUsernameUnique(username: String): Boolean{
        return true
    }

    private fun RegisterAccount(){

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