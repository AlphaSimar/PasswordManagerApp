package com.example.passwordmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TakeInput : AppCompatActivity() {

    private lateinit var edName:EditText
    private lateinit var edEmail:EditText
    private lateinit var edPassword:EditText
    private lateinit var btnSave :Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView:RecyclerView
    private var adapter:UserAdapter? = null

    private lateinit var sqlitehelper:SQLiteHelper
    private var std:UserViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_input)

        initView()
        initRecyclerView()
        sqlitehelper= SQLiteHelper(this)
        btnSave.setOnClickListener {
            addUser()
        }
        btnView.setOnClickListener {
            getUser()
        }
        btnUpdate.setOnClickListener { updateUser() }
        adapter?.setOnCLickItem { Toast.makeText(this,it.name, Toast.LENGTH_SHORT).show()
        edName.setText(it.name)
            edEmail.setText(it.email)
            edPassword.setText((it.password))
            std = it
        }

        }

    private fun getUser(){
        val stdList=sqlitehelper.getAllStudent()
        Log.e("pppp","${stdList.size}")

        adapter?.addItems(stdList)


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
                getUser()
            }else{
                Toast.makeText(this,"Record not Saved",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun updateUser(){
        val name = edName.text.toString()
        val email=edEmail.text.toString()
        val password= edPassword.text.toString()

        if(std==null) return

        val std =UserViewModel(id=std!!.id, name = name, email=email, password=password)
        val status=sqlitehelper.updateUser(std)
        if(status >-1){
            clearEdittext()
            getUser()
        }else{
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEdittext(){
        edName.setText("")
        edEmail.setText("")
        edPassword.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        edName=findViewById(R.id.et_input_username)
        edEmail=findViewById(R.id.et_input_email)
        edPassword=findViewById(R.id.et_input_password)
        btnSave=findViewById(R.id.btn_save)
        btnView=findViewById(R.id.btn_view)
        btnUpdate=findViewById(R.id.btn_Update)
    }
}