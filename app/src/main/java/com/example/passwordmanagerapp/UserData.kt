package com.example.passwordmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UserData : AppCompatActivity() {

    private lateinit var edName:EditText
    private lateinit var edEmail:EditText
    private lateinit var edPassword:EditText
    private lateinit var btnSave :Button
    private lateinit var btnView: Button

    private lateinit var sqlitehelper:SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        initView()
        sqlitehelper= SQLiteHelper(this)
        btnSave.setOnClickListener {
            addUser()
        }
        btnView.setOnClickListener {
            getUser()
        }

    }

    private fun getUser(){
        val stdList=sqlitehelper.getAllStudent()
        Log.e("pppp","${stdList.size}")

    }

    private fun addUser(){
        val name=edName.text.toString()
        val email=edEmail.text.toString()
        val password=edPassword.text.toString()
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please Enter Required Field",Toast.LENGTH_SHORT).show()
        }else{
            val std=UserViewModel(name=name,email=email, password = password)
            val status=sqlitehelper.insertUser(std)

            if(status >-1){
                Toast.makeText(this,"User Credentials Stored",Toast.LENGTH_SHORT).show()
                clearEdittext()
            }else{
                Toast.makeText(this,"Record not Saved",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun clearEdittext(){
        edName.setText("")
        edEmail.setText("")
        edPassword.setText("")
        edName.requestFocus()
    }



    private fun initView(){
        edName=findViewById(R.id.et_input_username)
        edEmail=findViewById(R.id.et_input_email)
        edPassword=findViewById(R.id.et_password)
        btnSave=findViewById(R.id.btn_save)
        btnView=findViewById(R.id.btn_view)






    }
}