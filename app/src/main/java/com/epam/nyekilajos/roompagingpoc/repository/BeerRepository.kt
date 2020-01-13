package com.epam.nyekilajos.roompagingpoc.repository

import com.epam.nyekilajos.roompagingpoc.model.database.Beer
import com.epam.nyekilajos.roompagingpoc.model.database.BeersDatabase
import com.epam.nyekilajos.roompagingpoc.model.database.Ingredients
import com.epam.nyekilajos.roompagingpoc.model.network.BeerDTO
import com.epam.nyekilajos.roompagingpoc.model.network.BeerService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class BeerRepository @Inject constructor(private val beerService: BeerService, private val beersDatabase: BeersDatabase) {

    suspend fun getBeers(): Flow<List<Beer>> {
        if (beersDatabase.beersDao().getCount() == 0) {
            refreshBeers()
        }
        return beersDatabase.beersDao().getBeers()
    }

    suspend fun refreshBeers() {
        updateDatabase(beerService.getBeers())
    }

    private suspend fun updateDatabase(it: List<BeerDTO>) {
        beersDatabase
                .beersDao()
                .updateAll(
                        it.mapNotNull { it.toBeer() }
                )
    }
}

fun BeerDTO.toBeer(): Beer? {
    return if (id != null && name != null) {
        Beer(
                id,
                name,
                Ingredients(
                        ingredients?.malt
                                ?.filter { malt -> malt?.name != null }
                                ?.map { malt ->
                                    malt?.name!!
                                } ?: listOf(),
                        ingredients?.hops
                                ?.filter { hop -> hop?.name != null }
                                ?.map { hop ->
                                    hop?.name!!
                                } ?: listOf(),
                        ingredients?.yeast ?: "")
        )
    } else {
        null
    }
}
