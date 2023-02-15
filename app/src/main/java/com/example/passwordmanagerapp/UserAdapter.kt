package com.example.passwordmanagerapp

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter :RecyclerView.Adapter<UserAdapter.userViewHolder>(){
    private var stdList:ArrayList<UserViewModel> = ArrayList()
    private var onClickItem: ((UserViewModel) -> Unit)? = null

    fun addItems(items:ArrayList<UserViewModel>){
        this.stdList = items
        notifyDataSetChanged()

    }

    fun setOnCLickItem(callback:(UserViewModel)->Unit){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = userViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_account,parent,false)
    )

    override fun onBindViewHolder(holder: userViewHolder,position: Int){
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size

    }

    class userViewHolder(var view:View):RecyclerView.ViewHolder(view){

        private var username=view.findViewById<TextView>(R.id.tvusername)
        private var email=view.findViewById<TextView>(R.id.tvemail)
        private var password=view.findViewById<TextView>(R.id.tvPassword)
        private var btnDelete=view.findViewById<TextView>(R.id.btnDelete)

        fun bindView(std:UserViewModel){
            username.text=std.name
            email.text=std.email

        }

    }
}