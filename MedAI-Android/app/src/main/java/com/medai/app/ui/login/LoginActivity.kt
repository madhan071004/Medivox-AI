package com.medai.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.medai.app.data.model.UiState
import com.medai.app.databinding.ActivityLoginBinding
import com.medai.app.ui.chat.ChatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var isSignUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToggle()
        setupButtons()
        observeState()
    }

    private fun setupToggle() {
        binding.btnToggleMode.setOnClickListener {
            isSignUp = !isSignUp
            binding.tilName.visibility = if (isSignUp) View.VISIBLE else View.GONE
            binding.tvHeading.text   = if (isSignUp) "Get started" else "Welcome back 👋"
            binding.tvSubheading.text= if (isSignUp) "Create your account" else "Please enter your details"
            binding.btnSubmit.text   = if (isSignUp) "Create Account" else "Sign In"
            binding.btnToggleMode.text = if (isSignUp) "Already have an account? Sign in" else "Don't have an account? Sign up"
        }
    }

    private fun setupButtons() {
        binding.btnSubmit.setOnClickListener {
            val name  = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val pass  = binding.etPassword.text.toString()
            val key   = binding.etApiKey.text.toString().trim()
            val finalName = if (isSignUp) name else email.substringBefore("@")
            viewModel.login(finalName, email, pass, key)
        }

        binding.btnGoogle.setOnClickListener {
            val key = binding.etApiKey.text.toString().trim()
            if (key.isBlank() || !key.startsWith("gsk_")) {
                binding.tilApiKey.error = "Enter your Groq API key first (gsk_...)"
                return@setOnClickListener
            }
            viewModel.login("Google User", "user@gmail.com", "google123", key)
        }

        binding.btnShowHideKey.setOnClickListener {
            val et = binding.etApiKey
            val sel = et.selectionStart
            if (et.inputType == 1) {
                et.inputType = 129 // password
                binding.btnShowHideKey.text = "👁️"
            } else {
                et.inputType = 1   // visible
                binding.btnShowHideKey.text = "🙈"
            }
            et.setSelection(sel)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSubmit.isEnabled = false
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        startActivity(Intent(this@LoginActivity, ChatActivity::class.java))
                        finish()
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSubmit.isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                    null -> { binding.progressBar.visibility = View.GONE }
                }
            }
        }
    }
}
