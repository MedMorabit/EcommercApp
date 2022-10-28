package com.example.ecommercapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommercapp.R
import com.example.ecommercapp.activities.ShoppingActivity
import com.example.ecommercapp.databinding.LoginFragmentBinding
import com.example.ecommercapp.util.*
import com.example.ecommercapp.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {
    lateinit var binding:LoginFragmentBinding
    val viewModel:LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=LoginFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvDontHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            loginButtonAccountOption.setOnClickListener {
                val email=editTxtEmail.text.trim().toString()
                val password=editTextPassword.text.toString()
                viewModel.login(email,password)

            }
            forgetPassword.setOnClickListener {
                setBottomSheetDialog {email->
                    viewModel.resetPassword(email)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.login.collect{ it ->
                when(it){
                    is Resource.Success ->{
                        binding.loginButtonAccountOption.revertAnimation()
                        Intent(requireContext(),ShoppingActivity::class.java).also { intent->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }


                    }
                    is Resource.Error ->{
                        withContext(Dispatchers.Main){
                            binding.loginButtonAccountOption.revertAnimation()
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading ->{
                        withContext(Dispatchers.Main){
                           binding.loginButtonAccountOption.startAnimation()
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
           viewModel.validate.collect{
               if(it.email is RegisterValidation.Failed){
                   withContext(Dispatchers.Main){
                       binding.editTxtEmail.apply {
                           error=it.email.message
                       }
                   }
               }else if(it.password is RegisterValidation.Failed){
                   withContext(Dispatchers.Main){
                       binding.editTextPassword.apply {
                           error=it.password.message
                       }
                   }
               }
           }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when(it){
                    is Resource.Success ->{
                        Snackbar.make(requireView(),"Email send successfully ${it.data}",Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error ->{
                        Snackbar.make(requireView(),"Error ${it.message}",Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Loading ->{

                    }
                }
            }

        }
    }



}