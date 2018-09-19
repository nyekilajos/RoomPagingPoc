package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beers")
data class Beer(

        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int,

        @ColumnInfo(name = "name", index = true)
        val name: String,

        @Embedded
        val ingredients: Ingredients

)
