package com.example.myfirstapp

import android.content.Context
import androidx.room.Room
import com.example.myfirstapp.data.WishDatabase
import com.example.myfirstapp.data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context) {
        database =
            Room.databaseBuilder(context, WishDatabase::class.java, name = "wishlist.db").build()
    }
}