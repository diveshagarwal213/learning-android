package com.example.myfirstapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-desc")
    val description: String = "",
)

object DummyWish {
    val wishList = listOf(
        Wish(
            id = 1,
            title = "Trip to Paris",
            description = "Visit the Eiffel Tower and explore the city of lights."
        ),
        Wish(
            id = 2,
            title = "Learn Guitar",
            description = "Master the basics of playing acoustic guitar."
        ),
        Wish(
            id = 3,
            title = "Skydiving",
            description = "Experience the thrill of freefall from 10,000 feet."
        ),
        Wish(
            id = 4,
            title = "Read 50 Books",
            description = "Challenge myself to read 50 books in a year."
        ),
        Wish(
            id = 5,
            title = "Start a Blog",
            description = "Share thoughts and experiences on a personal blog."
        )
    )
}
