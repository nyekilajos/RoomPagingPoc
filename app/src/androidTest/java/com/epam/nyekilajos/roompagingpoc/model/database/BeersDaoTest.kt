package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeersDaoTest {

    @Rule
    @JvmField
    var androidExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDb: BeersDatabase
    private lateinit var sut: BeersDao

    @Before
    fun setUp() {
        testDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), BeersDatabase::class.java).build()
        sut = testDb.beersDao()
    }

    @Test
    fun test() {
        val testSubscriber = sut.getBeers().test()

        sut.insertAll(listOf(IPA, STOUT))

        testSubscriber.assertValues(listOf(), listOf(IPA, STOUT))
    }

    @Test
    fun testJoin() {

        sut.insertAll(listOf(IPA, STOUT))
        sut.insertAllPeople(listOf(ISTVAN, LAJOS))

        sut.getPeopleWithBeers()
                .test()
                .assertValue(listOf(
                        PersonWithBeer(ISTVAN.name, STOUT.name),
                        PersonWithBeer(LAJOS.name, IPA.name)
                ))
    }

    @After
    fun tearDown() {
        testDb.close()
    }
}

private val IPA = Beer(0, "IPA", Ingredients(listOf(), listOf(), ""))
private val STOUT = Beer(1, "Stout", Ingredients(listOf(), listOf(), ""))

private val ISTVAN = Person(0, "Istvan", STOUT.id)
private val LAJOS = Person(1, "Lajos", IPA.id)
