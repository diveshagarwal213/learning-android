package com.example.myfirstapp

data class Category(
    var idCategory: String,
    var strCategory: String,
    var strCategoryThumb: String,
    var strCategoryDescription: String,
)

data class CategoriesResponse(
    var categories: List<Category>
)
