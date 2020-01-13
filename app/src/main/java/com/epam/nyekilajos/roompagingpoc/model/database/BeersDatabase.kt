package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(entities = [Beer::class], version = 2)
abstract class BeersDatabase : RoomDatabase() {

    abstract fun beersDao(): BeersDao

}

@Dao
abstract class BeersDao {

    @Query("SELECT COUNT(id) FROM beers")
    abstract suspend fun getCount(): Int

    @Query("SELECT * FROM beers ")
    abstract fun getBeers(): Flow<List<Beer>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertAll(items: List<Beer>)

    @Query("DELETE FROM beers")
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun updateAll(items: List<Beer>) {
        deleteAll()
        insertAll(items)
    }
}
