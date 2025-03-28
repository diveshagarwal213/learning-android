package com.example.myfirstapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var idCategory: String,
    var strCategory: String,
    var strCategoryThumb: String,
    var strCategoryDescription: String,
) : Parcelable

data class CategoriesResponse(
    var categories: List<Category>
)
