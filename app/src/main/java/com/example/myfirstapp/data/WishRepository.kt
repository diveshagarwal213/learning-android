package com.example.myfirstapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    suspend fun addAWish(wish: Wish) = wishDao.addAWish(wish)

    fun getWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getWishById(id: Long): Flow<Wish> = wishDao.getWishById(id)

    suspend fun updateWish(wish: Wish) = wishDao.updateAWish(wish)
    suspend fun deleteWish(wish: Wish) = wishDao.deleteAWish(wish)

}