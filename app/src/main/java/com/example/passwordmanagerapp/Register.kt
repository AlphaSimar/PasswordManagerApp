package com.example.passwordmanagerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Observable
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts.Intents
import android.text.style.ClickableSpan
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.passwordmanagerapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextSwitcher
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observer


@SuppressLint("CheckResult")


class Register : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        val nameStream = RxTextView.textChanges(binding.etFullname)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe{
            showNameExistAlert(it)
        }


        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }


        val usernameStream = RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }
        usernameStream.subscribe{
            showTextMinimalAlert(it, "Username")
        }

        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe{
            showTextMinimalAlert(it,"Password")
        }

        val passwordConfirmStream = io.reactivex.Observable.merge(
            RxTextView.textChanges(binding.etPassword)
                .skipInitialValue()
                .map{ password ->
                    password.toString() != binding.etConfirmPassword.text.toString()

                },
                RxTextView.textChanges(binding.etConfirmPassword)
                    .skipInitialValue()
                    .map { confirmPassword ->
                        confirmPassword.toString() != binding.etPassword.text.toString()
                    })
        passwordConfirmStream.subscribe{
            showPasswordConfirmAlert(it)
        }

        val invalidFieldStream = io.reactivex.Observable.combineLatest(
            nameStream,
            emailStream,
            usernameStream,
            passwordStream,
            passwordConfirmStream,
            { nameInvalid : Boolean, emailInvalid : Boolean,usernameInvalid : Boolean, passwordInvalid :Boolean,passwordConfirmInvalid :Boolean ->
                !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid &&passwordConfirmInvalid
            })
        invalidFieldStream.subscribe{ isValid ->
            if(isValid == true){
                binding.btnRegister.isEnabled =true
                binding.btnRegister.backgroundTintList= ContextCompat.getColorStateList(this,R.color.primary_color)
            } else{
                binding.btnRegister.isEnabled =true
                binding.btnRegister.backgroundTintList= ContextCompat.getColorStateList(this,R.color.primary_color)

            }
        }


        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            registerUser(email,password)

        }
        binding.account.setOnClickListener {
            startActivity(Intent(this,LoginKaro::class.java))
        }
    }
    private fun showNameExistAlert(isNotValid:Boolean){
        binding.etFullname.error = if(isNotValid) "Name cannot be Empty!" else null
    }
    private fun showTextMinimalAlert(isNotValid: Boolean, text: String){
        if(text=="Username")
            binding.etUsername.error = if(isNotValid)"$text should be more than 6 letters" else null
        else if(text=="Password")
            binding.etPassword.error = if(isNotValid)"$text should be more than 8 characters" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean){
        binding.etEmail.error = if(isNotValid)"Invalid Email" else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean){
        binding.etConfirmPassword.error = if(isNotValid)"Password does not match" else null
    }

    private fun registerUser(email : String , password : String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    startActivity(Intent(this, LoginKaro::class.java))
                    Toast.makeText(this,"Registeration Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,it.exception?.message, Toast.LENGTH_SHORT).show()

                }
            }
    }


}