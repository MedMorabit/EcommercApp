package com.example.ecommercapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommercapp.R
import com.example.ecommercapp.activities.ShoppingActivity

import com.example.ecommercapp.databinding.RegisterFragmentBinding
import com.example.ecommercapp.model.User
import com.example.ecommercapp.util.RegisterValidation
import com.example.ecommercapp.util.Resource
import com.example.ecommercapp.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.register_fragment) {
    private lateinit var binding:RegisterFragmentBinding
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=RegisterFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            registerButton.setOnClickListener {
                val user=User(
                    firstNameEdtRegister.text.toString().trim(),
                    lastNameRegister.text.toString().trim(),
                    emailRegister.text.toString().trim(),
                    ""

                )
                val password=passwordRegister.text.toString()
            viewModel.createAccountWithUserAndEmail(user,password)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.registerButton.startAnimation()
                    }
                    is Resource.Success ->{
                        binding.registerButton.revertAnimation()
                        Intent(requireContext(),ShoppingActivity::class.java).also { intent->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error ->{
                        Log.d("error",it.message.toString())
                        binding.registerButton.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.validate.collect{validation->
              if(validation.email is RegisterValidation.Failed){
                  withContext(Dispatchers.Main){
                      binding.emailRegister.apply {
                          this.requestFocus()
                          error=validation.email.message
                      }
                  }

              }
                if(validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.passwordRegister.apply {
                            error=validation.password.message
                        }
                    }
                }
            }
        }
    }

}