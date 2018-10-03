package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "people", foreignKeys = [ForeignKey(entity = Beer::class, parentColumns = ["id"], childColumns = ["favoriteBeerId"], onDelete = CASCADE, onUpdate = CASCADE)])
data class Person(

        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int,

        @ColumnInfo(name = "name", index = true)
        val name: String,

        @ColumnInfo(name = "favoriteBeerId", index = true)
        val favoriteBeerId: Int
)
