package com.example.myfirstapp

import android.app.Application

class WishListApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}