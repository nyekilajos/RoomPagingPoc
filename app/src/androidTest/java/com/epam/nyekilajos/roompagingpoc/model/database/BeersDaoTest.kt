package com.epam.nyekilajos.roompagingpoc.model.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
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
        testDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, BeersDatabase::class.java).build()
        sut = testDb.beersDao()
    }

    @Test
    fun test() = runBlocking {
        val beers = sut.getBeers()
        assertThat(beers.first(), equalTo(listOf()))

        sut.insertAll(listOf(IPA, STOUT))

        assertThat(beers.first(), equalTo(listOf(IPA, STOUT)))
    }


    @After
    fun tearDown() {
        testDb.close()
    }
}

private val IPA = Beer(0, "IPA", Ingredients(listOf(), listOf(), ""))
private val STOUT = Beer(1, "Stout", Ingredients(listOf(), listOf(), ""))
