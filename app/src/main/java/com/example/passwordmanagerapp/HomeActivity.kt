package com.example.passwordmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.passwordmanagerapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()


        binding.imageButton2.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton3.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton4.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton5.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton6.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton7.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton8.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton9.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton10.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton11.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton12.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton13.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton14.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton15.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton16.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton17.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton18.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton19.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton20.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }
        binding.imageButton21.setOnClickListener{
            startActivity(Intent(this, TakeInput::class.java))
        }


        binding.btnLogout.setOnClickListener {
            auth.signOut()
            Intent(this, LoginKaro::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(this, "Logout Succesfull", Toast.LENGTH_SHORT).show()
            }


        }
    }
}