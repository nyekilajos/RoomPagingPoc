package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Database(entities = [Beer::class], version = 1)
abstract class BeersDatabase : RoomDatabase() {

    abstract fun beersDao(): BeersDao

}

@Dao
abstract class BeersDao {

    @Query("SELECT COUNT(id) FROM beers")
    abstract fun getCount(): Single<Int>

    @Query("SELECT * FROM beers ")
    abstract fun getBeers(): Flowable<List<Beer>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract fun insertAll(items: List<Beer>)

    @Query("DELETE FROM beers")
    abstract fun deleteAll()

    @Transaction
    open fun updateAll(items: List<Beer>) {
        deleteAll()
        insertAll(items)
    }
}
