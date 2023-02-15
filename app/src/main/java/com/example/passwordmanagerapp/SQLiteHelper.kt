package com.example.passwordmanagerapp


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.passwordmanagerapp.UserViewModel
import kotlinx.coroutines.selects.select

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION =1
        private const val DATABASE_NAME="user.db"
        private const val TBL_USER ="tbl_user"

        private const val ID="id"
        private const val NAME ="name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTb1Student = ("CREATE TABLE" + TBL_USER+"("
                + ID+" INTEGER PRIMARY KEY,"+NAME+"TEXT, "
                +EMAIL+"TEXT,"+
                PASSWORD+"TEXT"+")")
        db?.execSQL(createTb1Student)



    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        onCreate(db)
    }

    fun insertUser(std:UserViewModel):Long{
        val db=this.writableDatabase

        val contentValues= ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(NAME,std.name)
        contentValues.put(EMAIL,std.email)
        contentValues.put(PASSWORD,std.password)

        val success = db.insert(TBL_USER,null,contentValues)
        db.close()
        return success

    }

    fun getAllStudent():ArrayList<UserViewModel>{
        val stdList:ArrayList<UserViewModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USER"
        val db =this.readableDatabase

        val cursor:Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null )
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }

        var id:Int
        var name:String
        var email:String
        var password:String

        if(cursor.moveToFirst()){
            do{
                id  =cursor.getInt(cursor.getColumnIndex("id"))
                name  =cursor.getString(cursor.getColumnIndex("name"))
                email  =cursor.getString(cursor.getColumnIndex("email"))
                password  =cursor.getString(cursor.getColumnIndex("password"))

                val std = UserViewModel(id=id,name=name,email=email,password=password)
                stdList.add(std)
            }while(cursor.moveToNext())
        }
        return stdList
    }

    fun updateUser(std:UserViewModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME,std.name)
        contentValues.put(EMAIL,std.email)
        contentValues.put(ID,std.id)
        contentValues.put(PASSWORD,std.password)

        val success = db.update(TBL_USER,contentValues,"id"+std.id,null)
        db.close()
        return success


    }
}