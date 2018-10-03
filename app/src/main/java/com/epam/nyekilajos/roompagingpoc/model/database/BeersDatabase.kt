package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Database(entities = [Beer::class, Person::class], version = 3)
abstract class BeersDatabase : RoomDatabase() {

    abstract fun beersDao(): BeersDao

    fun getFavoriteBeerByPerson(person: Person): Flowable<Beer> {
        return beersDao().getBeerById(person.favoriteBeerId)
    }
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

    @Query("SELECT * FROM people ")
    abstract fun getPeople(): Flowable<List<Person>>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    abstract fun insertAllPeople(items: List<Person>)

    @Query("DELETE FROM people")
    abstract fun deleteAllPeople()

    @Query("SELECT people.name, beers.name AS favoriteBeerName FROM beers INNER JOIN people ON beers.id = people.favoriteBeerId")
    abstract fun getPeopleWithBeers(): Single<List<PersonWithBeer>>

    @Transaction
    open fun updateAllPeople(items: List<Person>) {
        deleteAllPeople()
        insertAllPeople(items)
    }

    @Query("SELECT * FROM beers WHERE id = :id")
    abstract fun getBeerById(id: Int): Flowable<Beer>
}
