package com.example.passwordmanagerapp

import androidx.lifecycle.ViewModel
import kotlin.random.Random

data class UserViewModel(
    var id : Int=getAutoId(),
    var name :String="",
    var email:String="",
    var password:String=""
)
{

    companion object{
        fun getAutoId():Int{
            val random= java.util.Random()
            return random.nextInt(100)
        }
    }

}