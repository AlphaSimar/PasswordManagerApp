package com.example.passwordmanagerapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.passwordmanagerapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView


@SuppressLint("CheckResult")
class LoginKaro : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val usernameStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { username ->
                username.isEmpty()
            }
        usernameStream.subscribe{
            showTextMinimaAlert(it, "Email/Username")
        }

        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe{
            showTextMinimaAlert(it,"Password")
        }

        val invalidFieldStream = io.reactivex.Observable.combineLatest(

            usernameStream,

            passwordStream,
            { usernameInvalid : Boolean, passwordInvalid :Boolean ->
                 !usernameInvalid && !passwordInvalid
            })
        invalidFieldStream.subscribe{ isValid ->
            if(isValid){
                binding.btnLogin.isEnabled =true
                binding.btnLogin.backgroundTintList= ContextCompat.getColorStateList(this,R.color.primary_color)
            } else{
                binding.btnLogin.isEnabled =false
                binding.btnLogin.backgroundTintList= ContextCompat.getColorStateList(this,R.color.grey)

            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            loginUser(email,password)

        }
        binding.noAccount.setOnClickListener {
            startActivity(Intent(this,Register::class.java))

        }
    }

    private fun showTextMinimaAlert(isNotValid: Boolean, text: String){
        if(text=="Username")
            binding.etEmail.error = if(isNotValid)"$text cannot be Empty" else null
        else if(text=="Password")
            binding.etPassword.error = if(isNotValid)"$text cannot be Empty" else null
    }

    private fun loginUser(email : String, password :String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ login->
                if(login.isSuccessful) {
                    Intent(this, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "Login Succesfull", Toast.LENGTH_SHORT).show()
                    }
                }else{
                        Toast.makeText(this,login.exception?.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

