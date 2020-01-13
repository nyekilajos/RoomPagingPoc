package com.epam.nyekilajos.roompagingpoc.repository

import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDao
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.database.Ingredients
import com.epam.nyekilajos.roompagingpoc.model.network.BeerDTO
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BeerRepositoryTest {

    private lateinit var sut: BeerRepository

    @Mock
    private lateinit var beerServiceMock: BeerService

    @Mock
    private lateinit var beersDatabaseMock: BeersDatabase

    @Mock
    private lateinit var beersDaoMock: BeersDao

    @Before
    fun setUp() = runBlocking {
        whenever(beerServiceMock.getBeers()).thenReturn(listOf(IPA_DTO, STOUT_DTO))
        whenever(beersDatabaseMock.beersDao()).thenReturn(beersDaoMock)
        sut = BeerRepository(beerServiceMock, beersDatabaseMock)
    }

    @Test
    fun test() = runBlocking {
        sut.refreshBeers()

        verify(beersDaoMock).updateAll(eq(listOf(IPA, STOUT)))
    }
}

private val IPA_DTO = BeerDTO(0, "IPA")
private val STOUT_DTO = BeerDTO(1, "Stout")
private val IPA = Beer(0, "IPA", Ingredients(listOf(), listOf(), ""))
private val STOUT = Beer(1, "Stout", Ingredients(listOf(), listOf(), ""))
