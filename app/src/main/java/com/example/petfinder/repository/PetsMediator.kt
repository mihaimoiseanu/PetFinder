package com.example.petfinder.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.example.petfinder.networking.PetFinderAPI
import com.example.petfinder.networking.models.RemoteAnimal
import com.example.petfinder.repository.converters.toEntity
import com.example.petfinder.storage.database.AnimalDatabase
import com.example.petfinder.storage.database.EntityAnimal
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PetsMediator @Inject
constructor(
    private val animalDatabase: AnimalDatabase,
    private val petFinderAPI: PetFinderAPI
) : RxRemoteMediator<Int, EntityAnimal>() {

    private val petDao = animalDatabase.animalDao()

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, EntityAnimal>
    ): Single<MediatorResult> {
        val remoteKeySingle: Single<Int> = when (loadType) {
            LoadType.REFRESH -> Single.just(1)
            LoadType.PREPEND -> return Single.just(MediatorResult.Success(true))
            LoadType.APPEND -> {
                state.lastItemOrNull()
                    ?: return Single.just(MediatorResult.Success(endOfPaginationReached = true))
                val lastPage = state.pages.last()
                val nextPage = (lastPage.itemsBefore + lastPage.data.size) / 20 + 1
                Single.just(nextPage)
            }
        }
        return remoteKeySingle.subscribeOn(Schedulers.io())
            .flatMap { remoteKey ->
                petFinderAPI
                    .getPets(remoteKey)
                    .subscribeOn(Schedulers.io())
                    .map { response ->
                        animalDatabase.runInTransaction {
                            val entities = response.animals.map(RemoteAnimal::toEntity)
                            petDao.insert(*(entities.toTypedArray())).subscribe()
                        }
                        MediatorResult.Success(endOfPaginationReached = response.pagination.isLastPage) as MediatorResult//we need this cast because RX
                    }
                    .onErrorResumeNext { error ->
                        if (error is IOException || error is HttpException) {
                            return@onErrorResumeNext Single.just(MediatorResult.Error(error))
                        }
                        return@onErrorResumeNext Single.error(error)
                    }
            }
    }
}