package com.epam.nyekilajos.roompagingpoc.repository

import com.epam.nyekilajos.roompagingpoc.RxImmediateSchedulerRule
import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDao
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.network.BeerDTO
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class BeerRepositoryTest {

    @Rule
    @JvmField
    var rxImmediateSchedulerRule: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var sut: BeerRepository

    @Mock
    private lateinit var beerServiceMock: BeerService

    @Mock
    private lateinit var beersDatabaseMock: BeersDatabase

    @Mock
    private lateinit var beersDaoMock: BeersDao

    @Mock
    private lateinit var beersCallMock: Call<List<BeerDTO>>

    @Before
    fun setUp() {
        whenever(beerServiceMock.getBeers()).thenReturn(beersCallMock)
        whenever(beersDatabaseMock.beersDao()).thenReturn(beersDaoMock)
        sut = BeerRepository(beerServiceMock, beersDatabaseMock)
    }

    @Test
    fun test() {
        whenever(beersCallMock.execute()).thenReturn(Response.success(listOf(IPA_DTO, STOUT_DTO)))

        sut.refreshBeers().test()

        verify(beersDaoMock).updateAll(eq(listOf(IPA, STOUT)))
    }
}

private val IPA_DTO = BeerDTO(0, "IPA")
private val STOUT_DTO = BeerDTO(1, "Stout")
private val IPA = Beer(0, "IPA")
private val STOUT = Beer(1, "Stout")
